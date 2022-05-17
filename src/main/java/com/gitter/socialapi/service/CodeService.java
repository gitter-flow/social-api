package com.gitter.socialapi.service;
import com.gitter.socialapi.model.CodeEntity;
import com.gitter.socialapi.model.TypeCode;
import com.gitter.socialapi.payload.request.CodeCreationRequest;
import com.gitter.socialapi.payload.request.EditCodeRequest;
import com.gitter.socialapi.repository.CodeRepository;
import com.gitter.socialapi.repository.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.LinkOption;
import java.util.List;
import java.util.Optional;


@Service
public class CodeService {

    private final CodeRepository codeRepository;
    private final PublicationRepository publicationRepository;

    @Autowired
    public CodeService(CodeRepository codeRepository, PublicationRepository publicationRepository) {
        this.codeRepository = codeRepository;
        this.publicationRepository = publicationRepository;
    }


    public void addCode(CodeCreationRequest codeDto){
        CodeEntity code = new CodeEntity();
        code.setPublication(publicationRepository.getById(Long.valueOf(codeDto.getPublication())));
        code.setBucket(codeDto.getBucket());
        TypeCode typeCode = TypeCode.valueOf(codeDto.getTypeCode());
        if(typeCode==null){
            throw new IllegalStateException("The typeCode does not exist");
        }

        code.setTypeCode(typeCode);
        codeRepository.save(code);
    }

    public List<CodeEntity> getCodes(){
        return codeRepository.findAll();
    }

    public void updateCode(EditCodeRequest codeDto){
        Optional<CodeEntity> codeFound = codeRepository.findById(Long.valueOf(codeDto.getId()));
        if (codeFound.isPresent()) {
            codeFound.get().setPublication(publicationRepository.getById(Long.valueOf(codeDto.getPublication())));
            codeFound.get().setBucket(codeDto.getBucket());
            TypeCode typeCode = TypeCode.valueOf(codeDto.getTypeCode());
            if(typeCode==null){
                throw new IllegalStateException("The typeCode does not exist");
            }

            codeFound.get().setTypeCode(typeCode);
            codeRepository.save(codeFound.get());
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
