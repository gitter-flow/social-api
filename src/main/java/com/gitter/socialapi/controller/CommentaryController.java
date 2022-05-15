package com.gitter.socialapi.controller;

import com.gitter.socialapi.model.CodeEntity;
import com.gitter.socialapi.model.CommentaryEntity;
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







}
