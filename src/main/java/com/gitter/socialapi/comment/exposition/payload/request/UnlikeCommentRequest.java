package com.gitter.socialapi.comment.exposition.payload.request;

import lombok.Getter;

@Getter
public class UnlikeCommentRequest {
    private String commentId ;
    private String userId;
}
