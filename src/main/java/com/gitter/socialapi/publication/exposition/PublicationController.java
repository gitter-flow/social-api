package com.gitter.socialapi.publication.exposition;

import com.gitter.socialapi.publication.domain.PublicationEntity;
import com.gitter.socialapi.publication.application.PublicationService;
import com.gitter.socialapi.publication.exposition.payload.request.CreatePublicationRequest;
import com.gitter.socialapi.publication.exposition.payload.request.EditContentPublicationRequest;
import com.gitter.socialapi.publication.exposition.payload.request.EditLikePublicationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( value = "/publication")
public class PublicationController {
    private PublicationService publicationService;
    @Autowired
    PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @PostMapping
    public ResponseEntity<Long> savePublication(@RequestBody CreatePublicationRequest createPublicationRequest){
        Long publicationId = publicationService.addPublication(createPublicationRequest);
        return ResponseEntity.ok(publicationId);
    }
    @PutMapping
    public ResponseEntity<Void> editPublication(@RequestBody EditContentPublicationRequest contentPublicationRequest){
        publicationService.updateContentPublication(contentPublicationRequest);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/all")
    public ResponseEntity<List<PublicationEntity>> all() {
        return ResponseEntity.ok(publicationService.getPublications());
    }
    @PutMapping("/{id}")
    public void disablePublication(@PathVariable Long id){
        publicationService.deletePublication(id);
    }
    @PostMapping("/like")
    public ResponseEntity<Void> likePublication(@RequestBody EditLikePublicationRequest editLikePublicationRequest){
        publicationService.likePublication(editLikePublicationRequest);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/unlike")
    public ResponseEntity<Void> unlikePublication(@RequestBody EditLikePublicationRequest editLikePublicationRequest){
        publicationService.unlikePublication(editLikePublicationRequest);
        return ResponseEntity.ok().build();
    }
}
