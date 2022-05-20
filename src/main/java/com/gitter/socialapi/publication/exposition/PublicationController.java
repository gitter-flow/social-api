package com.gitter.socialapi.publication.exposition;

import com.gitter.socialapi.kernel.exceptions.InvalidParameterException;
import com.gitter.socialapi.publication.application.PublicationService;
import com.gitter.socialapi.publication.domain.Publication;
import com.gitter.socialapi.publication.exposition.payload.request.*;
import com.gitter.socialapi.publication.exposition.payload.response.CreatePublicationResponse;
import com.gitter.socialapi.publication.exposition.payload.response.GetPublicationResponse;
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
    
    @PutMapping
    public ResponseEntity<Void> updatePublication(@RequestBody UpdatePublicationRequest updateRequest) throws InvalidParameterException {
        publicationService.updatePublication(updateRequest);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/like")
    public ResponseEntity<Void> likePublication(@RequestBody UpdateLikePublicationRequest editLikePublicationRequest) throws InvalidParameterException {
        publicationService.likePublication(editLikePublicationRequest);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/unlike")
    public ResponseEntity<Void> unlikePublication(@RequestBody UpdateUnlikePublicationRequest updateUnlikePublicationRequest) throws InvalidParameterException {
        publicationService.unlikePublication(updateUnlikePublicationRequest);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping
    public ResponseEntity<Void> deletePublication(@RequestBody DeletePublicationRequest deletePublicationRequest) throws InvalidParameterException {
        publicationService.deletePublication(deletePublicationRequest);
        return ResponseEntity.ok().build();
    }
}
