package com.gitter.socialapi.controller;

import com.gitter.socialapi.model.CodeEntity;
import com.gitter.socialapi.payload.request.AddVersionCodeRequest;
import com.gitter.socialapi.payload.request.CreationCodeRequest;
import com.gitter.socialapi.payload.request.GetVersionCodeRequest;
import com.gitter.socialapi.service.CodeService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping( value = "/code")
public class CodeController {

    private CodeService codeService;

    @Autowired
    CodeController(CodeService codeService) {
        this.codeService = codeService;
    }



    @PostMapping("/add")
    public void saveCode(@RequestBody CreationCodeRequest code){

        codeService.addCode(code);
    }

    @PutMapping
    public void addVersion(@RequestBody AddVersionCodeRequest addVersionCodeRequest){
        codeService.addVersion(addVersionCodeRequest);
    }
    @GetMapping("version")
    public List<String> getVersion(@RequestBody GetVersionCodeRequest getVersionCodeRequest)
    {
       return codeService.getVersions(getVersionCodeRequest);
    }

    @GetMapping("/codes")
    List<CodeEntity> all() {
        return codeService.getCodes();
    }

    @DeleteMapping("/{id}")
    public void deleteCode(@PathVariable Long id){
        codeService.deleteCode(id);
    }







}
