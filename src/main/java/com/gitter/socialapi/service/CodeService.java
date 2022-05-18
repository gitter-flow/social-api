package com.gitter.socialapi.service;
import com.gitter.socialapi.model.CodeEntity;
import com.gitter.socialapi.model.TypeCode;
import com.gitter.socialapi.payload.request.CreationCodeRequest;
import com.gitter.socialapi.payload.request.AddVersionCodeRequest;
import com.gitter.socialapi.payload.request.EditCodeRequest;
import com.gitter.socialapi.payload.request.GetVersionCodeRequest;
import com.gitter.socialapi.payload.response.AddVersionCodeResponse;
import com.gitter.socialapi.repository.CodeRepository;
import com.gitter.socialapi.repository.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public void addCode(CreationCodeRequest codeDto){
        CodeEntity code = new CodeEntity();
        code.setPublication(publicationRepository.getById(Long.valueOf(codeDto.getPublication())));
        code.setBucket(codeDto.getBucket());
        TypeCode typeCode = TypeCode.valueOf(codeDto.getTypeCode());
        if(typeCode==null){
            throw new IllegalStateException("The typeCode does not exist");
        }

        code.setTypeCode(typeCode);
        List<String> versions = code.getVersions();
        versions.add(0,codeDto.getVersion());
        code.setVersions(versions);
        codeRepository.save(code);
    }

    public AddVersionCodeResponse addVersion(AddVersionCodeRequest addVersionCodeRequest){
        Optional<CodeEntity> codeFound = codeRepository.findById(Long.valueOf(addVersionCodeRequest.getId()));
        if(codeFound.isEmpty()){
            throw new NullPointerException("Code not found");
        }
        List<String> versions = codeFound.get().getVersions();
        versions.add(0, addVersionCodeRequest.getVersion());
        codeFound.get().setVersions(versions);
        codeRepository.save(codeFound.get());
         AddVersionCodeResponse addVersionCodeResponse = new AddVersionCodeResponse(codeFound.get().getId().toString());
        return addVersionCodeResponse;
    }

    public List<String> getVersions(GetVersionCodeRequest getVersionCodeRequest){
        Optional<CodeEntity> codeFound = codeRepository.findById(Long.valueOf(getVersionCodeRequest.getId()));
        if(codeFound.isEmpty()){
            throw new NullPointerException("Code not found");
        }

        return codeFound.get().getVersions();
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
