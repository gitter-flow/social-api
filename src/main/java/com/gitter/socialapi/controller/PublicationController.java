package com.gitter.socialapi.controller;

import com.gitter.socialapi.model.CommentaryEntity;
import com.gitter.socialapi.model.PublicationEntity;
import com.gitter.socialapi.payload.request.CreateContentPublicationRequest;
import com.gitter.socialapi.payload.request.EditContentPublicationRequest;
import com.gitter.socialapi.payload.request.EditLikePublicationRequest;
import com.gitter.socialapi.service.CommentaryService;
import com.gitter.socialapi.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void savePublication(@RequestBody CreateContentPublicationRequest createContentPublicationRequest){
        publicationService.addPublication(createContentPublicationRequest);
    }
    @PutMapping
    public void editPublication(@RequestBody EditContentPublicationRequest contentPublicationRequest){
        publicationService.updateContentPublication(contentPublicationRequest);
    }

    @GetMapping("/publications")
    List<PublicationEntity> all() {
        return publicationService.getPublications();
    }

    @PostMapping("/{id}")
    public void disablePublication(@PathVariable Long id){

        publicationService.deletePublication(id);
    }
    @PostMapping("/like")
    public void likePublication(@RequestBody EditLikePublicationRequest editLikePublicationRequest){
        publicationService.likePublication(editLikePublicationRequest);
    }

    @PostMapping("/unlike")
    public void unlikePublication(@RequestBody EditLikePublicationRequest editLikePublicationRequest){
        publicationService.unlikePublication(editLikePublicationRequest);
    }






}
