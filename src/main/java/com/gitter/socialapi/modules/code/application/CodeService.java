package com.gitter.socialapi.modules.code.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gitter.socialapi.kernel.exceptions.InvalidCodeVersionException;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Slf4j
public class CodeService {
    private final CodeRepository codeRepository;
    private final PublicationRepository publicationRepository;
    
    private final CodeAPIRepository codeAPIRepository;
    
    private final String baseURL;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    private final static String BUCKET_FILENAME_FORMAT="user-%s/code-%s.%s";
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
                        String.format(BUCKET_FILENAME_FORMAT, publication.getUser().getId(), code.getId(), CodeType.extension(code.getCodeType())),
                        new RunAndSaveCodeRequest.Data(codeRequest.getCode(), code.getCodeType().getText())
                )
        );
        code.getVersions().add( 
                new Version(apiResponse.getMinioCode().getVersionId(), apiResponse.getMinioResultCode().getVersionId()));

        codeRepository.save(code);
        
        return CreateCodeMapper.getResponse(code, apiResponse.getResultOfExec());
    }
    public RunCodeResponse runCode(RunCodeRequest codeRequest) throws IOException, InterruptedException, InvalidParameterException {
        RunCodeAPIResponse apiResp = codeAPIRepository.runCode(new RunCodeAPIRequest(CodeAPIMapper.toAPICodeType(CodeType.fromString(codeRequest.getCodeType())), codeRequest.getCode()));
        return new RunCodeResponse(apiResp.getOutput());
    }
    public RetrieveCodeResponse getCodeFromId(String id) throws InvalidParameterException, IOException, URISyntaxException, InterruptedException {
        Code code = getCodeFromIdString(id);
        RetrieveCodeMapper mapper = new RetrieveCodeMapper(baseURL);
        List<Version> codeVersions =  code.getVersions();
        String lastCode = getCodeVersion(code.getId(),codeVersions.get(codeVersions.size() - 1).getCodeVersion());
        return mapper.getResponse(code, lastCode);
    }
    
    public RetrieveCodeVersionsResponse getVersions(String codeId) throws InvalidParameterException {
        Code code = getCodeFromIdString(codeId);
        return new RetrieveCodeVersionsResponse(code.getVersions());
    }

    public String getCodeVersion(String codeId, String versionId) throws InvalidParameterException, IOException, InterruptedException, URISyntaxException {
        Code code = getCodeFromIdString(codeId);
        String fileName = String.format(BUCKET_FILENAME_FORMAT, code.getPublication().getUser().getId(), code.getPublication().getCode().getId(), CodeType.extension(code.getCodeType()));
        return codeAPIRepository.getVersionCode(fileName, versionId);
    }
    
    @Transactional
    public void deleteCode(DeleteCodeRequest deleteCodeRequest) throws InvalidParameterException {
        Code code = getCodeFromIdString(deleteCodeRequest.getId());
        String request = String.format("DELETE FROM versions WHERE code_id = '%s'", code.getId());
        entityManager.createNativeQuery(request).executeUpdate();
        codeRepository.delete(code);
    }
}
