package com.gitter.socialapi.modules.code.exposition;

import com.gitter.socialapi.modules.code.application.CodeService;
import com.gitter.socialapi.modules.code.exposition.payload.request.*;
import com.gitter.socialapi.modules.code.exposition.payload.request.code_api.GetCodeListVersionsRequest;
import com.gitter.socialapi.modules.code.exposition.payload.response.*;
import com.gitter.socialapi.kernel.exceptions.InvalidParameterException;
import com.gitter.socialapi.kernel.exceptions.InvalidCodeTypeException;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping(
        value = "/code",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class CodeController {
    private final CodeService codeService;
    
    @Autowired
    CodeController(CodeService codeService) {
        this.codeService = codeService;
    }
    
    @PostMapping("/save")
    @PreAuthorize("@authService.tokenIsValidForPublicationWithId(#code.publicationId, #authentication)")
    public ResponseEntity<SaveCodeResponse> createCode(@RequestBody CreateCodeRequest code, KeycloakAuthenticationToken authentication) throws InvalidParameterException, InvalidCodeTypeException, IOException, InterruptedException {
        return ResponseEntity.ok(codeService.saveCode(code));
    }
    @PostMapping("/run")
    public ResponseEntity<RunCodeResponse> runCode(@RequestBody RunCodeRequest addVersionCodeRequest) throws IOException, InterruptedException, InvalidParameterException {
        return ResponseEntity.ok(codeService.runCode(addVersionCodeRequest));
    }
    @GetMapping("/{id}")
    public ResponseEntity<RetrieveCodeResponse> getCodeFromId(@PathVariable String id) throws InvalidParameterException, IOException, URISyntaxException, InterruptedException {
        return ResponseEntity.ok(codeService.getCodeFromId(id));
    }
    @GetMapping("/versions")
    public ResponseEntity<RetrieveCodeVersionsResponse> getVersions(@RequestParam(value= "codeId") String codeId) throws InvalidParameterException {
        RetrieveCodeVersionsResponse versions = codeService.getVersions(codeId);
       return ResponseEntity.ok(versions);
    }
    @GetMapping("/version")
    public ResponseEntity<String> getCodeVersion(
            @RequestParam(value= "codeId") String codeId, @RequestParam(value = "versionId") String versionId)
            throws InvalidParameterException, IOException, InterruptedException, URISyntaxException {
        String code = codeService.getCodeVersion(codeId, versionId); 
        return ResponseEntity.ok(code);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("@authService.tokenIsValidForCodeWithId(#deleteCodeRequest.id, #authentication)")
    public ResponseEntity<String> deleteCode(@RequestBody DeleteCodeRequest deleteCodeRequest, KeycloakAuthenticationToken authentication) throws InvalidParameterException {
        codeService.deleteCode(deleteCodeRequest);
        return ResponseEntity.ok(String.format("Code %s successfully deleted", deleteCodeRequest.getId()));
    }
}
