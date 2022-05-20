package com.gitter.socialapi.comment.exposition;

import com.gitter.socialapi.comment.domain.CommentEntity;
import com.gitter.socialapi.comment.application.CommentService;
import com.gitter.socialapi.comment.exposition.payload.request.UpdateCommentRequest;
import com.gitter.socialapi.comment.exposition.payload.request.LikeCommentRequest;
import com.gitter.socialapi.comment.exposition.payload.request.GetCommentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( value = "/comment")
public class CommentController {
    private CommentService commentService;
    @Autowired
    CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping
    public ResponseEntity<Long> createComment(@RequestBody CommentEntity comment){
        Long id = commentService.addComment(comment);
        return ResponseEntity.ok(id);
    }
    @GetMapping("/all")
    public ResponseEntity<List<CommentEntity>> getAll() {
        return ResponseEntity.ok(commentService.getComments());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping
    public ResponseEntity<Void> updateComment(@RequestBody UpdateCommentRequest contentCommentRequest) {
        commentService.updateContentComment(contentCommentRequest);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<List<CommentEntity>> getCommentPublication(@RequestBody GetCommentRequest getCommentPublicationRequest){
        return ResponseEntity.ok(commentService.getCommentPublication(getCommentPublicationRequest));
    }
    @PostMapping("/like")
    public ResponseEntity<Void> likePublication(@RequestBody LikeCommentRequest editLikeCommentRequest){
        commentService.likeComment(editLikeCommentRequest);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/unlike")
    public ResponseEntity<Void> unlikeComment(@RequestBody LikeCommentRequest editLikeCommentRequest){
        commentService.unlikeComment(editLikeCommentRequest);
        return ResponseEntity.ok().build();
    }
}
