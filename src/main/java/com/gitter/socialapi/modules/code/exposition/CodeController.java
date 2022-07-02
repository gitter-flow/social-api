package com.gitter.socialapi.modules.code.exposition;

import com.gitter.socialapi.modules.code.application.CodeService;
import com.gitter.socialapi.modules.code.exposition.payload.request.*;
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
    @PutMapping("/run")
    public RunCodeResponse runCode(@RequestBody RunCodeRequest addVersionCodeRequest) throws InvalidParameterException, IOException, InterruptedException {
        return codeService.runCode(addVersionCodeRequest);
    }
    @GetMapping("/{id}")
    public ResponseEntity<RetrieveCodeResponse> getCodeFromId(@PathVariable String id) throws InvalidParameterException {
        return ResponseEntity.ok(codeService.getCodeFromId(id));
    }
    @GetMapping("/versions")
    public ResponseEntity<RetrieveCodeVersionsResponse> getVersions(@RequestBody RetrieveVersionCodeRequest getVersionCodeRequest) throws InvalidParameterException {
        RetrieveCodeVersionsResponse versions = codeService.getVersions(getVersionCodeRequest);
       return ResponseEntity.ok(versions);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("@authService.tokenIsValidForCodeWithId(#deleteCodeRequest.id, #authentication)")
    public ResponseEntity<String> deleteCode(@RequestBody DeleteCodeRequest deleteCodeRequest, KeycloakAuthenticationToken authentication) throws InvalidParameterException {
        codeService.deleteCode(deleteCodeRequest);
        return ResponseEntity.ok(String.format("Code %s successfully deleted", deleteCodeRequest.getId()));
    }
}
