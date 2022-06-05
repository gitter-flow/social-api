package com.gitter.socialapi.modules.code.application;

import com.gitter.socialapi.modules.code.domain.Code;
import com.gitter.socialapi.modules.code.exposition.payload.request.AddVersionCodeRequest;
import com.gitter.socialapi.modules.code.exposition.payload.request.CreateCodeRequest;
import com.gitter.socialapi.modules.code.exposition.payload.request.DeleteCodeRequest;
import com.gitter.socialapi.modules.code.exposition.payload.request.RetrieveVersionCodeRequest;
import com.gitter.socialapi.modules.code.exposition.payload.response.AddVersionCodeResponse;
import com.gitter.socialapi.modules.code.exposition.payload.response.CreateCodeResponse;
import com.gitter.socialapi.modules.code.exposition.payload.response.RetrieveCodeResponse;
import com.gitter.socialapi.modules.code.exposition.payload.response.RetrieveCodeVersionsResponse;
import com.gitter.socialapi.modules.code.infrastructure.CodeRepository;
import com.gitter.socialapi.kernel.exceptions.InvalidParameterException;
import com.gitter.socialapi.kernel.exceptions.InvalidCodeTypeException;
import com.gitter.socialapi.kernel.exceptions.NoSuchEntityException;
import com.gitter.socialapi.modules.publication.domain.Publication;
import com.gitter.socialapi.modules.publication.infrastructure.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class CodeService {
    private final CodeRepository codeRepository;
    private final PublicationRepository publicationRepository;
    private final String baseURL;
    
    @Autowired
    public CodeService(CodeRepository codeRepository, PublicationRepository publicationRepository, @Value("${application.url}") String baseURL) {
        this.codeRepository = codeRepository;
        this.publicationRepository = publicationRepository;
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
    
    public CreateCodeResponse createCode(CreateCodeRequest codeRequest) throws InvalidParameterException, InvalidCodeTypeException {
        Publication publication = getPublicationFromIdString(codeRequest.getPublicationId());
        Code code = codeRepository.save(CreateCodeMapper.toCode(codeRequest, publication));
        return CreateCodeMapper.getResponse(code);
    }
    
    public RetrieveCodeResponse getCodeFromId(String id) throws InvalidParameterException {
        Code code = getCodeFromIdString(id);
        RetrieveCodeMapper mapper = new RetrieveCodeMapper(baseURL);
        return mapper.getResponse(code);
    }
    public AddVersionCodeResponse addVersion(AddVersionCodeRequest addVersionCodeRequest) throws InvalidParameterException {
        Code code = getCodeFromIdString(addVersionCodeRequest.getId());
        code.getVersions().add(UUID.randomUUID().toString());
        codeRepository.save(code);
        return new AddVersionCodeResponse(code.getVersions().get(0));
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
