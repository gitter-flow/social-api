package com.gitter.socialapi.comment;

import com.gitter.socialapi.comment.payload.request.UpdateCommentRequest;
import com.gitter.socialapi.comment.payload.request.EditLikeCommentaryRequest;
import com.gitter.socialapi.comment.payload.request.GetCommentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( value = "/commentary")
public class CommentController {
    private CommentService commentaryService;
    @Autowired
    CommentController(CommentService commentaryService) {
        this.commentaryService = commentaryService;
    }
    @PostMapping("/")
    public ResponseEntity<Long> createCommentary(@RequestBody CommentEntity commentary){
        Long id = commentaryService.addCommentary(commentary);
        return ResponseEntity.ok(id);
    }
    @GetMapping("/all")
    public ResponseEntity<List<CommentEntity>> getAll() {
        return ResponseEntity.ok(commentaryService.getCommentarys());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommentary(@PathVariable Long id) {
        commentaryService.deleteCommentary(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping
    public ResponseEntity<Void> editCommentary(@RequestBody UpdateCommentRequest contentCommentaryRequest) {
        commentaryService.updateContentCommentary(contentCommentaryRequest);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<List<CommentEntity>> getCommentaryPublication(@RequestBody GetCommentRequest getCommentaryPublicationRequest){
        return ResponseEntity.ok(commentaryService.getCommentaryPublication(getCommentaryPublicationRequest));
    }
    @PostMapping("/like")
    public ResponseEntity<Void> likePublication(@RequestBody EditLikeCommentaryRequest editLikeCommentaryRequest){
        commentaryService.likeCommentary(editLikeCommentaryRequest);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/unlike")
    public ResponseEntity<Void> unlikeCommentary(@RequestBody EditLikeCommentaryRequest editLikeCommentaryRequest){
        commentaryService.unlikeCommentary(editLikeCommentaryRequest);
        return ResponseEntity.ok().build();
    }
}
