package com.gitter.socialapi.publication.exposition;

import com.gitter.socialapi.kernel.exceptions.InvalidParameterException;
import com.gitter.socialapi.publication.application.PublicationService;
import com.gitter.socialapi.publication.exposition.payload.request.*;
import com.gitter.socialapi.publication.exposition.payload.response.CreatePublicationResponse;
import com.gitter.socialapi.publication.exposition.payload.response.GetPublicationResponse;
import com.gitter.socialapi.publication.exposition.payload.request.GetUserPublicationRequest;
import com.gitter.socialapi.publication.exposition.payload.response.GetUserPublicationsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/publication",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class PublicationController {
    private PublicationService publicationService;
    @Autowired
    PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @PostMapping
    public ResponseEntity<CreatePublicationResponse> createPublication(@RequestBody CreatePublicationRequest createPublicationRequest) throws InvalidParameterException {
        CreatePublicationResponse response = publicationService.createPublication(createPublicationRequest);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<GetPublicationResponse> retrievePublicationByID(@PathVariable String id) throws InvalidParameterException {
        return ResponseEntity.ok(publicationService.getPublicationByID(id));
    }
    @GetMapping("/user")
    public ResponseEntity<GetUserPublicationsResponse> getUserPublications(@RequestBody GetUserPublicationRequest getRequest) throws InvalidParameterException {
        return ResponseEntity.ok(publicationService.getUserPublications(getRequest.getId()));
    }
    @PutMapping
    public ResponseEntity<String> updatePublication(@RequestBody UpdatePublicationRequest updateRequest) throws InvalidParameterException {
        publicationService.updatePublication(updateRequest);
        return ResponseEntity.ok(String.format("Publication %s updated", updateRequest.getId()));
    }
    @PostMapping("/like")
    public ResponseEntity<String> likePublication(@RequestBody UpdateLikePublicationRequest likePublicationRequest) throws InvalidParameterException {
        publicationService.likePublication(likePublicationRequest);
        return ResponseEntity.ok(String.format(
                "Publication %s liked by user %s",
                likePublicationRequest.getPublicationId(),
                likePublicationRequest.getUserId()));
    }
    @PostMapping("/unlike")
    public ResponseEntity<String> unlikePublication(@RequestBody UpdateUnlikePublicationRequest unlikePublicationRequest) throws InvalidParameterException {
        publicationService.unlikePublication(unlikePublicationRequest);
         return ResponseEntity.ok(String.format(
                "Publication %s liked by user %s",
                 unlikePublicationRequest.getPublicationId(),
                 unlikePublicationRequest.getUserId()));
    }
    @DeleteMapping
    public ResponseEntity<String> deletePublication(@RequestBody DeletePublicationRequest deletePublicationRequest) throws InvalidParameterException {
        publicationService.deletePublication(deletePublicationRequest);
        return ResponseEntity.ok(String.format("Publication %s deleted", deletePublicationRequest.getId()));
    }
}
