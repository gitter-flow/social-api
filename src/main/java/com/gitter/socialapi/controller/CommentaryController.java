package com.gitter.socialapi.controller;

import com.gitter.socialapi.model.CodeEntity;
import com.gitter.socialapi.model.CommentaryEntity;
import com.gitter.socialapi.payload.request.*;
import com.gitter.socialapi.service.CodeService;
import com.gitter.socialapi.service.CommentaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( value = "/commentary")
public class CommentaryController {

    private CommentaryService commentaryService;

    @Autowired
    CommentaryController(CommentaryService commentaryService) {
        this.commentaryService = commentaryService;
    }

    @PostMapping("/add")
    public void saveCommentary(@RequestBody CommentaryEntity commentary){
        commentaryService.addCommentary(commentary);
    }

    @GetMapping("/commentarys")
    List<CommentaryEntity> all() {
        return commentaryService.getCommentarys();
    }

    @DeleteMapping("/{id}")
    public void deleteCommentary(@PathVariable Long id){
        commentaryService.deleteCommentary(id);
    }

    @PutMapping
    public void editCommentary(@RequestBody EditContentCommentaryRequest contentCommentaryRequest){
        commentaryService.updateContentCommentary(contentCommentaryRequest);
    }
    @GetMapping
    public List<CommentaryEntity> getCommentaryPublication(@RequestBody GetCommentaryPublicationRequest getCommentaryPublicationRequest){
        return commentaryService.getCommentaryPublication(getCommentaryPublicationRequest);
    }

    @PostMapping("/like")
    public void likePublication(@RequestBody EditLikeCommentaryRequest editLikeCommentaryRequest){
        commentaryService.likeCommentary(editLikeCommentaryRequest);
    }

    @PostMapping("/unlike")
    public void unlikePublication(@RequestBody EditLikeCommentaryRequest editLikeCommentaryRequest){
        commentaryService.unlikeCommentary(editLikeCommentaryRequest);
    }




}
