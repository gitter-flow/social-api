package com.gitter.socialapi.controller;

import com.gitter.socialapi.model.CodeEntity;
import com.gitter.socialapi.service.CodeService;
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
    public void saveCode(@RequestBody CodeEntity code){
         codeService.addCode(code);
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
