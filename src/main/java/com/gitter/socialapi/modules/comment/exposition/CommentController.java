package com.gitter.socialapi.modules.comment.exposition;

import com.gitter.socialapi.modules.auth.AuthService;
import com.gitter.socialapi.modules.comment.application.CommentService;
import com.gitter.socialapi.modules.comment.exposition.payload.request.*;
import com.gitter.socialapi.modules.comment.exposition.payload.response.CreateCommentResponse;
import com.gitter.socialapi.modules.comment.exposition.payload.response.RetrievePublicationCommentsResponse;
import com.gitter.socialapi.kernel.exceptions.InvalidParameterException;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( value = "/comment")
public class CommentController {
    private final CommentService commentService;
    private final AuthService authService;
    @Autowired
    CommentController(CommentService commentService, AuthService authService) {
        this.commentService = commentService;
        this.authService = authService;
    }
    
    @PostMapping
    @PreAuthorize("@authService.tokenIsValidForUserWithId(#comment.userId, #authentication)")
    public ResponseEntity<CreateCommentResponse> createComment(@RequestBody CreateCommentRequest comment, KeycloakAuthenticationToken authentication) throws InvalidParameterException {
        return ResponseEntity.ok(commentService.createComment(comment));
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
    @PutMapping
    @PreAuthorize("@authService.tokenIsValidForCommentWithId(#updateRequest.id, #authentication)")
    public ResponseEntity<String> updateComment(@RequestBody UpdateCommentRequest updateRequest, KeycloakAuthenticationToken authentication) throws InvalidParameterException {
        commentService.updateContentComment(updateRequest);
        return ResponseEntity.ok(String.format("Comment %s updated", updateRequest.getId()));
    }
    
    @PostMapping("/like")
    @PreAuthorize("@authService.tokenIsValidForUserWithId(#likeRequest.userId, #authentication)")
    public ResponseEntity<String> likePublication(@RequestBody LikeCommentRequest likeRequest, KeycloakAuthenticationToken authentication) throws InvalidParameterException {
        commentService.likeComment(likeRequest);
        return ResponseEntity.ok(String.format("Comment %s liked by user %s", likeRequest.getCommentId(), likeRequest.getUserId()));
    }
    @PostMapping("/unlike")
    @PreAuthorize("@authService.tokenIsValidForUserWithId(#unlikeRequest.userId, #authentication)")
    public ResponseEntity<String> unlikeComment(@RequestBody UnlikeCommentRequest unlikeRequest, KeycloakAuthenticationToken authentication) throws InvalidParameterException {
        commentService.unlikeComment(unlikeRequest);
        return ResponseEntity.ok(String.format("Comment %s unliked by user %s", unlikeRequest.getCommentId(), unlikeRequest.getUserId()));
    }
    @DeleteMapping
    @PreAuthorize("@authService.tokenIsValidForCommentWithId(#deleteRequest.id, #authentication)")
    public ResponseEntity<String> deleteComment(@RequestBody DeleteCommentRequest deleteRequest, KeycloakAuthenticationToken authentication) {
        commentService.deleteComment(deleteRequest.getId());
        return ResponseEntity.ok(String.format("Comment %s deleted", deleteRequest.getId()));
    }
}
