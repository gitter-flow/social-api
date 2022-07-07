package com.gitter.socialapi.modules.code.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitter.socialapi.kernel.exceptions.InvalidCodeVersionException;
import com.gitter.socialapi.kernel.exceptions.InvalidParameterException;
import com.gitter.socialapi.kernel.exceptions.UnexpectedInternalRequestException;
import com.gitter.socialapi.kernel.exceptions.UnexpectedInternalResponseException;
import com.gitter.socialapi.modules.code.exposition.payload.request.code_api.RunAndSaveCodeRequest;
import com.gitter.socialapi.modules.code.exposition.payload.request.code_api.RunCodeAPIRequest;
import com.gitter.socialapi.modules.code.exposition.payload.response.code_api.RunAndSaveCodeResponse;
import com.gitter.socialapi.modules.code.exposition.payload.response.code_api.RunCodeAPIResponse;
import com.google.gson.Gson;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CodeAPIRepository {
    private final String codeApiURL;
    
    private final HttpClient client;
    
    private final Gson gson;
    @Autowired
    public CodeAPIRepository( @Value("${code.api.url}") String codeApiURL) {
        this.codeApiURL = codeApiURL;
        this.client = HttpClient.newHttpClient();
        this.gson = new Gson();
    }
    
    public RunCodeAPIResponse runCode(RunCodeAPIRequest request) throws IOException, InterruptedException {
        HashMap<String, String> body = new HashMap<>(){{
            put("mod", request.getMod());
            put("data", request.getData());
        }};
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(body);

        String uriStr = String.format("%s/resources/exec", codeApiURL);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(URI.create(uriStr))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
    
        HttpResponse<String> response = client.send(httpRequest,
                HttpResponse.BodyHandlers.ofString());
        
        if(response.statusCode() / 100 != 2) {
            throw  UnexpectedInternalRequestException.forRequest(uriStr, response.statusCode(), response.body());
        }
        return new RunCodeAPIResponse(response.body());
    }

    public RunAndSaveCodeResponse runAndSaveCode(RunAndSaveCodeRequest request) throws IOException, InterruptedException {

        JSONObject body =  new JSONObject();
        try {
            body.put("namefile", request.getFileName());
            body.put("data", new JSONObject(new HashMap<>() {{
                put("mod", request.getData().mod);
                put("data", request.getData().data);
                }}
            ));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(body);

        String uriStr = String.format("%s/resources/minio/code", codeApiURL);
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .uri(URI.create(uriStr))
                .PUT(HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();
        HttpResponse<String> response = client.send(httpRequest,
                HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() / 100 != 2) {
            throw  UnexpectedInternalRequestException.forRequest(uriStr, response.statusCode(), response.body());
        }
        JSONObject jsonObject =new JSONObject(response.body());
        
        if(!jsonObject.has("result")) {
            throw UnexpectedInternalResponseException.forRequest(uriStr, response.body());
        }
        JSONObject result = (JSONObject)jsonObject.get("result");

        return gson.fromJson(result.toString(), RunAndSaveCodeResponse.class);
    }

    public String getVersionCode(String fileName, String versionId) throws IOException, InterruptedException, URISyntaxException, InvalidParameterException {


        URI uri = new URIBuilder(String.format("%s/resources/minio/fileversion?namefile=%s&version_of_file=%s", codeApiURL, fileName, versionId)).build();
        var httpRequest = HttpRequest.newBuilder(uri)
                .header("Content-Type", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(httpRequest,
                HttpResponse.BodyHandlers.ofString());
        System.out.println("-------------------------------");
        System.out.println(response.body());
        System.out.println("-------------------------------");
        if(response.statusCode() == 400) {
            throw InvalidCodeVersionException.of(fileName, versionId);
        }else if(response.statusCode() / 100 != 2) {
            throw  UnexpectedInternalRequestException.forRequest(uri.toString(), response.statusCode(), response.body());
        }
        return response.body();
    }
}
