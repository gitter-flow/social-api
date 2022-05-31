package com.gitter.socialapi.publication.application;

import com.gitter.socialapi.code.application.CodeService;
import com.gitter.socialapi.code.domain.Code;
import com.gitter.socialapi.kernel.exceptions.InvalidParameterException;
import com.gitter.socialapi.kernel.exceptions.NoSuchEntityException;
import com.gitter.socialapi.publication.domain.Publication;
import com.gitter.socialapi.publication.exposition.payload.request.*;
import com.gitter.socialapi.publication.exposition.payload.response.*;
import com.gitter.socialapi.publication.infrastructure.PublicationRepository;
import com.gitter.socialapi.user.application.UserService;
import com.gitter.socialapi.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class PublicationService {

    private final PublicationRepository publicationRepository;
    private final UserService userService;
    private final CodeService codeService;
    
    private final String baseURL;

    @Autowired
    public PublicationService(PublicationRepository publicationRepository,
                              UserService userService, 
                              CodeService codeService,
                              @Value("${application.url}") String baseURL
                              ) {
        this.publicationRepository = publicationRepository;
        this.userService = userService;
        this.codeService = codeService;
        this.baseURL = baseURL;
    }
    
    
    private Long getIdToLong(String idStr) throws InvalidParameterException {
        long id;
        try {
            id = Long.parseLong(idStr);
        } catch (NumberFormatException nfe) {
            throw InvalidParameterException.forField("id", idStr);
        }
        return id;
    }
    public Publication getPublicationFromIdString(String idStr) throws InvalidParameterException {
        Long id = getIdToLong(idStr);
        Optional<Publication> publication = publicationRepository.findById(id);

        if(publication.isEmpty()){
            throw NoSuchEntityException.withId(Publication.class.getSimpleName(), id);
        }
        return publication.get();
    }

    public CreatePublicationResponse createPublication(CreatePublicationRequest publicationRequest) throws InvalidParameterException {
        Publication publication = new Publication();
        publication.setUser(userService.getUserFromStringId(publicationRequest.getUserId()));

        if(publicationRequest.getSharedPublicationId() != null) {
            publication.setSharedPublication(getPublicationFromIdString(publicationRequest.getSharedPublicationId()));
            publicationRepository.save(publication);
            return CreatePublicationMapper.toResponse(publication);
        } else if(publicationRequest.getParentPublicationId() != null) {
            publication.setParentPublication(getPublicationFromIdString(publicationRequest.getParentPublicationId()));
        }
        if(publicationRequest.getCodeId() != null) {
            publication.setCode(codeService.getCodeFromIdString(publicationRequest.getCodeId()));
        }
        if(publicationRequest.getContent() != null) {
            publication.setContent(publicationRequest.getContent());
        }
        publicationRepository.save(publication);
        
        return CreatePublicationMapper.toResponse(publication);
    }
    public RetrievePublicationResponse getPublicationByID(String id) throws InvalidParameterException {
        Publication publication = getPublicationFromIdString(id);
        RetrievePublicationMapper mapper = new RetrievePublicationMapper(baseURL);
        return mapper.toResponse(publication);
    }

    public RetrieveAllPublicationsResponse getAll(RetrieveAllPublicationsRequest getRequest) {
        Page<Publication> publicationList = publicationRepository.findAll(PageRequest.of(getRequest.getPageNumber(), getRequest.getNumberPerPage()));
        RetrieveAllPublicationsMapper mapper = new RetrieveAllPublicationsMapper(baseURL);
        return mapper.toResponse(publicationList);
    }
    public RetrieveUserPublicationsResponse getUserPublications(RetrieveUserPublicationRequest getRequest) throws InvalidParameterException {
        RetrieveUserPublicationMapper mapper = new RetrieveUserPublicationMapper(baseURL);
        PageRequest page = PageRequest.of(getRequest.getPageNumber(), getRequest.getNumberPerPage());
        List<Publication> publicationList = publicationRepository.selectWhereUserIdEquals(getIdToLong(getRequest.getId()), page);
        return mapper.getResponse(publicationList);
    }
    public void updatePublication(UpdatePublicationRequest updateRequest) throws InvalidParameterException {
        Publication publication = getPublicationFromIdString(updateRequest.getId());
        if(updateRequest.getContent() != null) {
            publication.setContent(updateRequest.getContent());
        }
        if(updateRequest.getCodeId() != null) {
            Code code = codeService.getCodeFromIdString(updateRequest.getCodeId());
            publication.setCode(code);
        }
        publicationRepository.save(publication);
    }
    
    public RetrieveNewPublicationsResponse getNewPublications(RetrieveNewPublicationsRequest getRequest) {
        List<Publication> publications = new ArrayList<>(publicationRepository.selectWhereUserFollows(getRequest.getUserId(), PageRequest.of(getRequest.getPageNumber(), getRequest.getNumberPerPage())));
        RetrieveNewPublicationsMapper mapper = new RetrieveNewPublicationsMapper(baseURL);
        return mapper.toResponse(publications);
    }
    public void likePublication(UpdateLikePublicationRequest likePublicationRequest) throws InvalidParameterException {
        Publication publication = getPublicationFromIdString(likePublicationRequest.getPublicationId());
        User user = userService.getUserFromStringId(likePublicationRequest.getUserId());
        publication.getLikedBy().add(user);
        publicationRepository.save(publication);
    }
    public void unlikePublication(UpdateUnlikePublicationRequest unlikePublicationRequest) throws InvalidParameterException {
        Publication publication = getPublicationFromIdString(unlikePublicationRequest.getPublicationId());
        User user = userService.getUserFromStringId(unlikePublicationRequest.getUserId());
        publication.getLikedBy().remove(user);
        publicationRepository.save(publication);
    }
    public void deletePublication(DeletePublicationRequest deleteRequest) throws InvalidParameterException {
        publicationRepository.deleteById(deleteRequest.getId());
    }
}
