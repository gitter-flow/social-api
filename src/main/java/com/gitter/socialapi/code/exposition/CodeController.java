package com.gitter.socialapi.code.exposition;

import com.gitter.socialapi.code.application.CodeService;
import com.gitter.socialapi.code.exposition.payload.request.AddVersionCodeRequest;
import com.gitter.socialapi.code.exposition.payload.request.CreateCodeRequest;
import com.gitter.socialapi.code.exposition.payload.request.DeleteCodeRequest;
import com.gitter.socialapi.code.exposition.payload.request.RetrieveVersionCodeRequest;
import com.gitter.socialapi.code.exposition.payload.response.AddVersionCodeResponse;
import com.gitter.socialapi.code.exposition.payload.response.CreateCodeResponse;
import com.gitter.socialapi.code.exposition.payload.response.RetrieveCodeResponse;
import com.gitter.socialapi.code.exposition.payload.response.RetrieveCodeVersionsResponse;
import com.gitter.socialapi.kernel.exceptions.InvalidParameterException;
import com.gitter.socialapi.kernel.exceptions.InvalidCodeTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/code",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class CodeController {
    private CodeService codeService;
    @Autowired
    CodeController(CodeService codeService) {
        this.codeService = codeService;
    }
    
    @PostMapping
    public ResponseEntity<CreateCodeResponse> createCode(@RequestBody CreateCodeRequest code) throws InvalidParameterException, InvalidCodeTypeException {
        return ResponseEntity.ok(codeService.createCode(code));
    }
    @PutMapping("/run")
    public AddVersionCodeResponse addVersion(@RequestBody AddVersionCodeRequest addVersionCodeRequest) throws InvalidParameterException {
        return codeService.addVersion(addVersionCodeRequest);
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
    public ResponseEntity<String> deleteCode(@RequestBody DeleteCodeRequest deleteCodeRequest) throws InvalidParameterException {
        codeService.deleteCode(deleteCodeRequest);
        return ResponseEntity.ok(String.format("Code %s successfully deleted", deleteCodeRequest.getId()));
    }
}
