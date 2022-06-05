package com.gitter.socialapi.modules.comment.exposition.payload.request;

import lombok.Getter;

@Getter
public class UnlikeCommentRequest {
    private String commentId ;
    private String userId;
}
