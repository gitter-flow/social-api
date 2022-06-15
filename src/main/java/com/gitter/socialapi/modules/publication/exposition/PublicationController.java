package com.gitter.socialapi.modules.publication.exposition;

import com.gitter.socialapi.auth.AuthService;
import com.gitter.socialapi.kernel.exceptions.InvalidParameterException;
import com.gitter.socialapi.modules.publication.application.PublicationService;
import com.gitter.socialapi.modules.publication.exposition.payload.request.*;
import com.gitter.socialapi.modules.publication.exposition.payload.response.*;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(
        value = "/publication",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class PublicationController {
    private final PublicationService publicationService;
    
    private final AuthService authService;
    
    @Autowired
    PublicationController(PublicationService publicationService, AuthService authService) {
        this.publicationService = publicationService;
        this.authService = authService;
    }

    @PostMapping
    @PreAuthorize("@authService.tokenIsValidForUserWithId(#createPublicationRequest.userId, #authentication)")
    public ResponseEntity<CreatePublicationResponse> createPublication(@RequestBody CreatePublicationRequest createPublicationRequest, KeycloakAuthenticationToken authentication) throws InvalidParameterException {
        CreatePublicationResponse response = publicationService.createPublication(createPublicationRequest);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<RetrievePublicationResponse> retrievePublicationByID(@PathVariable String id) throws InvalidParameterException {
        return ResponseEntity.ok(publicationService.getPublicationByID(id));
    }
    @GetMapping(
            value = "/all",
            params = {"page", "size"}
    )
    public ResponseEntity<List<RetrieveAllPublicationsResponse>> retrieveAllPublications(
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size
    ) {
        RetrieveAllPublicationsRequest getRequest = new RetrieveAllPublicationsRequest(page, size);
        return ResponseEntity.ok(publicationService.getAll(getRequest));
    }
    @GetMapping(
            value = "/user/{userId}",
            params = {"page", "size"}
    )
    public ResponseEntity<List<RetrieveUserPublicationsResponse>> retrieveUserPublications(
            @PathVariable(value= "userId") String userId,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size
    ) throws InvalidParameterException {
        RetrieveUserPublicationRequest getRequest = new RetrieveUserPublicationRequest(userId, page, size);
        return ResponseEntity.ok(publicationService.getUserPublications(getRequest));
    }
    @GetMapping(
            value = "/news/{userId}",
            params = {"page", "size"}
    )
    public ResponseEntity<List<RetrieveNewPublicationsResponse>> retrieveNewPublications(
            @PathVariable(value= "userId") String userId,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size
    ) {
        RetrieveNewPublicationsRequest getRequest = new RetrieveNewPublicationsRequest(userId, page, size);
        return ResponseEntity.ok(publicationService.getNewPublications(getRequest));
    }
    @PutMapping
    @PreAuthorize("@authService.tokenIsValidForPublicationWithId(#updateRequest.id, #authentication)")
    public ResponseEntity<String> updatePublication(@RequestBody UpdatePublicationRequest updateRequest, KeycloakAuthenticationToken authentication) throws InvalidParameterException {
        publicationService.updatePublication(updateRequest);
        return ResponseEntity.ok(String.format("Publication %s updated", updateRequest.getId()));
    }
    @PostMapping("/like")
    @PreAuthorize("@authService.tokenIsValidForUserWithId(#likePublicationRequest.userId, #authentication)")
    public ResponseEntity<String> likePublication(@RequestBody UpdateLikePublicationRequest likePublicationRequest,  KeycloakAuthenticationToken authentication) throws InvalidParameterException {
        publicationService.likePublication(likePublicationRequest);
        return ResponseEntity.ok(String.format(
                "Publication %s liked by user %s",
                likePublicationRequest.getPublicationId(),
                likePublicationRequest.getUserId()));
    }
    @PostMapping("/unlike")
    @PreAuthorize("@authService.tokenIsValidForUserWithId(#unlikePublicationRequest.userId, #authentication)")
    public ResponseEntity<String> unlikePublication(@RequestBody UpdateUnlikePublicationRequest unlikePublicationRequest, KeycloakAuthenticationToken authentication) throws InvalidParameterException {
         publicationService.unlikePublication(unlikePublicationRequest);
         return ResponseEntity.ok(String.format(
                "Publication %s liked by user %s",
                 unlikePublicationRequest.getPublicationId(),
                 unlikePublicationRequest.getUserId()));
    }
    @DeleteMapping
    @PreAuthorize("@authService.tokenIsValidForPublicationWithId(#deletePublicationRequest.id, #authentication)")
    public ResponseEntity<String> deletePublication(@RequestBody DeletePublicationRequest deletePublicationRequest, KeycloakAuthenticationToken authentication) throws InvalidParameterException {
        publicationService.deletePublication(deletePublicationRequest);
        return ResponseEntity.ok(String.format("Publication %s deleted", deletePublicationRequest.getId()));
    }
}
