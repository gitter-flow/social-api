package com.gitter.socialapi.code.application;

import com.gitter.socialapi.code.domain.Code;
import com.gitter.socialapi.code.exposition.payload.request.AddVersionCodeRequest;
import com.gitter.socialapi.code.exposition.payload.request.CreateCodeRequest;
import com.gitter.socialapi.code.exposition.payload.request.DeleteCodeRequest;
import com.gitter.socialapi.code.exposition.payload.request.RetrieveVersionCodeRequest;
import com.gitter.socialapi.code.exposition.payload.response.AddVersionCodeResponse;
import com.gitter.socialapi.code.exposition.payload.response.CreateCodeResponse;
import com.gitter.socialapi.code.exposition.payload.response.RetrieveCodeResponse;
import com.gitter.socialapi.code.exposition.payload.response.RetrieveCodeVersionsResponse;
import com.gitter.socialapi.code.infrastructure.CodeRepository;
import com.gitter.socialapi.kernel.exceptions.InvalidParameterException;
import com.gitter.socialapi.kernel.exceptions.InvalidTypeCodeException;
import com.gitter.socialapi.kernel.exceptions.NoSuchEntityException;
import com.gitter.socialapi.kernel.utils.DateGenerator;
import com.gitter.socialapi.publication.domain.Publication;
import com.gitter.socialapi.publication.infrastructure.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;


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
    
    private static Long getIdFromString(String idStr) throws InvalidParameterException {
        long id;
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException nfe) {
            throw InvalidParameterException.forField("id", idStr);
        }
        return id;
    }
    public Code getCodeFromIdString(String idStr) throws InvalidParameterException {
        Long id = getIdFromString(idStr);
        Optional<Code> code = codeRepository.findById(id);
        if(code.isEmpty()){
            throw NoSuchEntityException.withId(org.aspectj.apache.bcel.classfile.Code.class.getSimpleName(), id);
        }
        return code.get();
    }
    public Publication getPublicationFromIdString(String idStr) throws InvalidParameterException {
        Long id = getIdFromString(idStr);
        Optional<Publication> publication = publicationRepository.findById(id);

        if(publication.isEmpty()){
            throw NoSuchEntityException.withId(Publication.class.getSimpleName(), id);
        }
        return publication.get();
    }
    
    public CreateCodeResponse createCode(CreateCodeRequest codeRequest) throws InvalidParameterException, InvalidTypeCodeException {
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
        DateGenerator dateGenerator = new DateGenerator();
        Code code = getCodeFromIdString(addVersionCodeRequest.getId());
        code.getVersions().add(dateGenerator.now());
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
