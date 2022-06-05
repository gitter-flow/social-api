package com.gitter.socialapi.modules.comment.application;

import com.gitter.socialapi.modules.comment.domain.Comment;
import com.gitter.socialapi.modules.comment.exposition.payload.request.CreateCommentRequest;
import com.gitter.socialapi.modules.comment.exposition.payload.response.CreateCommentResponse;
import com.gitter.socialapi.modules.publication.domain.Publication;
import com.gitter.socialapi.modules.user.domain.User;


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
