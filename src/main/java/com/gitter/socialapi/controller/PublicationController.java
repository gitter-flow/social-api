package com.gitter.socialapi.controller;

import com.gitter.socialapi.model.CommentaryEntity;
import com.gitter.socialapi.model.PublicationEntity;
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

    @PostMapping("/add")
    public void savePublication(@RequestBody PublicationEntity publication){
        publicationService.addPublication(publication);
    }

    @GetMapping("/publications")
    List<PublicationEntity> all() {
        return publicationService.getPublications();
    }

    @DeleteMapping("/{id}")
    public void deletePublication(@PathVariable Long id){
        publicationService.deletePublication(id);
    }







}
