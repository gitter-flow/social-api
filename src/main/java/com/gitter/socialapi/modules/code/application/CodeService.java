package com.gitter.socialapi.modules.code.application;

import com.gitter.socialapi.modules.code.domain.Code;
import com.gitter.socialapi.modules.code.domain.CodeType;
import com.gitter.socialapi.modules.code.domain.Version;
import com.gitter.socialapi.modules.code.exposition.payload.request.*;
import com.gitter.socialapi.modules.code.exposition.payload.request.code_api.RunAndSaveCodeRequest;
import com.gitter.socialapi.modules.code.exposition.payload.request.code_api.RunCodeAPIRequest;
import com.gitter.socialapi.modules.code.exposition.payload.response.*;
import com.gitter.socialapi.modules.code.exposition.payload.response.code_api.RunAndSaveCodeResponse;
import com.gitter.socialapi.modules.code.exposition.payload.response.code_api.RunCodeAPIResponse;
import com.gitter.socialapi.modules.code.infrastructure.CodeAPIRepository;
import com.gitter.socialapi.modules.code.infrastructure.CodeRepository;
import com.gitter.socialapi.kernel.exceptions.InvalidParameterException;
import com.gitter.socialapi.kernel.exceptions.InvalidCodeTypeException;
import com.gitter.socialapi.kernel.exceptions.NoSuchEntityException;
import com.gitter.socialapi.modules.publication.domain.Publication;
import com.gitter.socialapi.modules.publication.infrastructure.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;


@Service
public class CodeService {
    private final CodeRepository codeRepository;
    private final PublicationRepository publicationRepository;
    
    private final CodeAPIRepository codeAPIRepository;
    private final String baseURL;
    
    @Autowired
    public CodeService(CodeRepository codeRepository, PublicationRepository publicationRepository, CodeAPIRepository codeAPIRepository, @Value("${application.url}") String baseURL) {
        this.codeRepository = codeRepository;
        this.publicationRepository = publicationRepository;
        this.codeAPIRepository = codeAPIRepository;
        this.baseURL = baseURL;
    }
    public Code getCodeFromIdString(String id) throws InvalidParameterException {
        Optional<Code> code = codeRepository.findById(id);
        if(code.isEmpty()){
            throw NoSuchEntityException.withId(org.aspectj.apache.bcel.classfile.Code.class.getSimpleName(), id);
        }
        return code.get();
    }
    public Publication getPublicationFromIdString(String id) throws InvalidParameterException {
        Optional<Publication> publication = publicationRepository.findById(id);

        if(publication.isEmpty()){
            throw NoSuchEntityException.withId(Publication.class.getSimpleName(), id);
        }
        return publication.get();
    }
    
    public SaveCodeResponse saveCode(CreateCodeRequest codeRequest) throws InvalidParameterException, InvalidCodeTypeException, IOException, InterruptedException {
        Publication publication = getPublicationFromIdString(codeRequest.getPublicationId());
        Code code;
        if(publication.getCode() == null) {
            code = codeRepository.save(CreateCodeMapper.toCode(codeRequest, publication));
        } else {
            code = getCodeFromIdString(publication.getCode().getId());
            code.setCodeType(CodeType.fromString(codeRequest.getCodeType()));
        }
        
        RunAndSaveCodeResponse apiResponse = codeAPIRepository.runAndSaveCode(
                new RunAndSaveCodeRequest(
                        String.format("user-%s/code-%s", publication.getUser().getId(), code.getId()),
                        new RunAndSaveCodeRequest.Data(codeRequest.getCode(), code.getCodeType().getText())
                )
        );
        code.getVersions().add(
                new Version(apiResponse.getMinioCode().versionId, apiResponse.getMinioResultCode().versionId));
        codeRepository.save(code);
        return CreateCodeMapper.getResponse(code, apiResponse.getResultOfExec());
    }
    
    public RunCodeResponse runCode(RunCodeRequest codeRequest) throws IOException, InterruptedException {
        RunCodeAPIResponse apiResp = codeAPIRepository.runCode(new RunCodeAPIRequest(codeRequest.getCodeType(), codeRequest.getCodeType()));
        return new RunCodeResponse(apiResp.getOutput());
    }
    public RetrieveCodeResponse getCodeFromId(String id) throws InvalidParameterException {
        Code code = getCodeFromIdString(id);
        RetrieveCodeMapper mapper = new RetrieveCodeMapper(baseURL);
        return mapper.getResponse(code);
    }
    
    public RetrieveCodeVersionsResponse getVersions(RetrieveVersionCodeRequest getVersionCodeRequest) throws InvalidParameterException {
        Code code = getCodeFromIdString(getVersionCodeRequest.getId());
        return new RetrieveCodeVersionsResponse(code.getVersions());
    }

    public void deleteCode(DeleteCodeRequest deleteCodeRequest) throws InvalidParameterException {
        Code code = getCodeFromIdString(deleteCodeRequest.getId());
        codeRepository.delete(code);
    }
}
