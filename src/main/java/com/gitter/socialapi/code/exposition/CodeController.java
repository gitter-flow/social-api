package com.gitter.socialapi.code.exposition;

import com.gitter.socialapi.code.application.CodeService;
import com.gitter.socialapi.code.exposition.payload.request.AddVersionCodeRequest;
import com.gitter.socialapi.code.exposition.payload.request.CreationCodeRequest;
import com.gitter.socialapi.code.exposition.payload.request.GetVersionCodeRequest;
import com.gitter.socialapi.code.exposition.payload.response.AddVersionCodeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping( value = "/code")
public class CodeController {
    private CodeService codeService;
    @Autowired
    CodeController(CodeService codeService) {
        this.codeService = codeService;
    }
    
    @PostMapping("/")
    public ResponseEntity<Long> saveCode(@RequestBody CreationCodeRequest code){
        Optional<Long> codeId = Optional.ofNullable(codeService.addCode(code));
        return ResponseEntity.of(codeId);
    }

    @PutMapping
    public AddVersionCodeResponse addVersion(@RequestBody AddVersionCodeRequest addVersionCodeRequest){
        return codeService.addVersion(addVersionCodeRequest);
    }
    @GetMapping("/versions")
    public ResponseEntity<List<String>> getVersions(@RequestBody GetVersionCodeRequest getVersionCodeRequest) {
        Optional<List<String>> versions = Optional.ofNullable(codeService.getVersions(getVersionCodeRequest));
       return ResponseEntity.of(versions);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCode(@PathVariable Long id){
        codeService.deleteCode(id);
        return ResponseEntity.noContent().build();
    }







}
