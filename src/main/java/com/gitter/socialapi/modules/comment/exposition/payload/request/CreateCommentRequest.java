package com.gitter.socialapi.modules.comment.exposition.payload.request;

import lombok.Getter;

@Getter
public class CreateCommentRequest {
    private String userId;
    private String publicationId;
    private String content;
}
