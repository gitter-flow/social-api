package com.gitter.socialapi.service;
import com.gitter.socialapi.model.CodeEntity;
import com.gitter.socialapi.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CodeService {

    private final CodeRepository codeRepository;

    @Autowired
    public CodeService(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }


    public void addCode(CodeEntity code){
        codeRepository.save(code);
    }

    public List<CodeEntity> getCodes(){
        return codeRepository.findAll();
    }

    public void updateCode(CodeEntity code){
        Optional<CodeEntity> codeFound = codeRepository.findById(code.getId());
        if (codeFound.isPresent()) {
            codeFound.get().setTypeCode(code.getTypeCode());
            codeFound.get().setBucket(code.getBucket());
            codeFound.get().setPublication(code.getPublication());
        }
        else {
            throw new NullPointerException("Code not found");
        }
    }

    public void deleteCode(Long id){
        Optional<CodeEntity> codeEntityFound = codeRepository.findById(id);
        if(codeEntityFound.isPresent()){
            codeRepository.delete(codeEntityFound.get());
        }
        else {
            throw new NullPointerException("Code not found");
        }
    }
}
