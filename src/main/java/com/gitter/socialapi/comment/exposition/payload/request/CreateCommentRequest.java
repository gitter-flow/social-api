package com.gitter.socialapi.comment.exposition.payload.request;

import lombok.Getter;

@Getter
public class CreateCommentRequest {
    private String userId;
    private String publicationId;
    private String content;
}
