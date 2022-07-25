package com.gitter.socialapi.modules.publication.application;

import com.gitter.socialapi.modules.code.application.CodeService;
import com.gitter.socialapi.modules.code.domain.Code;
import com.gitter.socialapi.kernel.exceptions.InvalidParameterException;
import com.gitter.socialapi.kernel.exceptions.NoSuchEntityException;
import com.gitter.socialapi.modules.code.exposition.payload.request.DeleteCodeRequest;
import com.gitter.socialapi.modules.publication.exposition.payload.request.*;
import com.gitter.socialapi.modules.publication.exposition.payload.response.*;
import com.gitter.socialapi.modules.publication.domain.Publication;
import com.gitter.socialapi.modules.publication.infrastructure.PublicationRepository;
import com.gitter.socialapi.modules.user.application.UserService;
import com.gitter.socialapi.modules.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
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
    
    public Publication getPublicationFromIdString(String idStr) throws InvalidParameterException {
        Optional<Publication> publication = publicationRepository.findById(idStr);

        if(publication.isEmpty()){
            throw NoSuchEntityException.withId(Publication.class.getSimpleName(), idStr);
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
        return RetrievePublicationMapper.toResponse(publication);
    }
    public List<RetrieveAllPublicationsResponse> getAll(RetrieveAllPublicationsRequest getRequest) {
        List<Publication> publicationList = publicationRepository.findAllOrderByCreatedAt(PageRequest.of(getRequest.getPageNumber(), getRequest.getNumberPerPage()));
        return RetrieveAllPublicationsMapper.toResponse(publicationList); 
    }
    public List<RetrieveUserPublicationsResponse> getUserPublications(RetrieveUserPublicationRequest getRequest) throws InvalidParameterException {
        PageRequest page = PageRequest.of(getRequest.getPageNumber(), getRequest.getNumberPerPage());
        List<Publication> publicationList = publicationRepository.selectWhereUserIdEquals(getRequest.getId(), page);
        return RetrieveUserPublicationMapper.getResponse(publicationList);
    }
    public List<RetrieveNewPublicationsResponse> getNewPublications(RetrieveNewPublicationsRequest getRequest) {
        List<Publication> publications = new ArrayList<>(publicationRepository.selectWhereUserFollows(getRequest.getUserId(), PageRequest.of(getRequest.getPageNumber(), getRequest.getNumberPerPage())));
        return RetrieveNewPublicationsMapper.toResponse(publications);
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
        Publication publication = getPublicationFromIdString(deleteRequest.getId());
        System.out.println("HHHHHHHHHHHHHHHHHHHHHHH");
        if(publication.getCode() != null) {
            System.out.println("--------------------------------");
            System.out.println("IDD: " + publication.getCode().getId());
            String codeId = publication.getCode().getId();
//            publication.setCode(new Code());
//            publicationRepository.save(publication);
            log.info(String.format("Deleting code %s...", codeId));
            codeService.deleteCode(new DeleteCodeRequest(codeId));
        }
        
        log.warn(String.format("Deleting publication %s...", deleteRequest.getId()));
        publicationRepository.deleteById(deleteRequest.getId());
    }
}
