package com.gitter.socialapi.modules.comment.exposition;

import com.gitter.socialapi.modules.comment.application.CommentService;
import com.gitter.socialapi.modules.comment.exposition.payload.request.*;
import com.gitter.socialapi.modules.comment.exposition.payload.response.CreateCommentResponse;
import com.gitter.socialapi.modules.comment.exposition.payload.response.RetrievePublicationCommentsResponse;
import com.gitter.socialapi.kernel.exceptions.InvalidParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( value = "/comment")
public class CommentController {
    private final CommentService commentService;
    @Autowired
    CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    
    @PostMapping
    public ResponseEntity<CreateCommentResponse> createComment(@RequestBody CreateCommentRequest comment) throws InvalidParameterException {
        return ResponseEntity.ok(commentService.createComment(comment));
    }
    @DeleteMapping
    public ResponseEntity<String> deleteComment(@RequestBody DeleteCommentRequest deleteRequest) {
        commentService.deleteComment(deleteRequest.getId());
        return ResponseEntity.ok(String.format("Comment %s deleted", deleteRequest.getId()));
    }
    @PutMapping
    public ResponseEntity<String> updateComment(@RequestBody UpdateCommentRequest updateRequest) throws InvalidParameterException {
        commentService.updateContentComment(updateRequest);
        return ResponseEntity.ok(String.format("Comment %s updated", updateRequest.getId()));
    }
    @GetMapping(
            value="/{publicationId}",
            params = {"page", "size"}
    )
    public ResponseEntity<List<RetrievePublicationCommentsResponse>> getCommentPublication(
            @PathVariable("publicationId") String publicationId,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ){
        RetrievePublicationCommentsRequest getRequest = new RetrievePublicationCommentsRequest(publicationId, page, size);
        return ResponseEntity.ok(commentService.getCommentPublication(getRequest));
    }
    @PostMapping("/like")
    public ResponseEntity<String> likePublication(@RequestBody LikeCommentRequest likeRequest) throws InvalidParameterException {
        commentService.likeComment(likeRequest);
        return ResponseEntity.ok(String.format("Comment %s liked by user %s", likeRequest.getCommentId(), likeRequest.getUserId()));
    }
    @PostMapping("/unlike")
    public ResponseEntity<String> unlikeComment(@RequestBody UnlikeCommentRequest unlikeRequest) throws InvalidParameterException {
        commentService.unlikeComment(unlikeRequest);
        return ResponseEntity.ok(String.format("Comment %s unliked by user %s", unlikeRequest.getCommentId(), unlikeRequest.getUserId()));
    }
}
