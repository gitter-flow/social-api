package com.gitter.socialapi.comment.application;

import com.gitter.socialapi.comment.domain.Comment;
import com.gitter.socialapi.comment.exposition.payload.request.CreateCommentRequest;
import com.gitter.socialapi.comment.exposition.payload.response.CreateCommentResponse;
import com.gitter.socialapi.publication.domain.Publication;
import com.gitter.socialapi.user.domain.User;


public class CreateCommentMapper {
    public static Comment toComment(CreateCommentRequest commentRequest, User user, Publication publication) {
        return new Comment(
                user,
                publication,
                commentRequest.getContent()
        );
    }
    public static CreateCommentResponse toResponse(Comment comment) {
        return new CreateCommentResponse(comment.getId());
    }
}
