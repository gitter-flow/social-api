package com.gitter.socialapi.modules.code.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitter.socialapi.kernel.exceptions.UnexpectedInternalRequestException;
import com.gitter.socialapi.kernel.exceptions.UnexpectedInternalResponseException;
import com.gitter.socialapi.modules.code.exposition.payload.request.code_api.RunAndSaveCodeRequest;
import com.gitter.socialapi.modules.code.exposition.payload.request.code_api.RunCodeAPIRequest;
import com.gitter.socialapi.modules.code.exposition.payload.response.code_api.RunAndSaveCodeResponse;
import com.gitter.socialapi.modules.code.exposition.payload.response.code_api.RunCodeAPIResponse;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
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
        HashMap<String, String> body = new HashMap<>(){{
            put("namefile", request.getFileName());
            put("data", new HashMap<>(){{
                put("mod", request.getData().mod);
                put("data", request.getData().data);
            }}.toString());
        }};
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper
                .writeValueAsString(body);

        String uriStr = String.format("%s/resources/minio/code", codeApiURL);
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
        JSONObject jsonObject =new JSONObject(response.body());
        
        if(jsonObject.has("result")) {
            throw  UnexpectedInternalResponseException.forRequest(uriStr, response.body());
        }
        JSONObject result = (JSONObject)jsonObject.get("result");
        
        return gson.fromJson(result.toString(), RunAndSaveCodeResponse.class);
    }
}
