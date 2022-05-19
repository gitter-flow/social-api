package com.gitter.socialapi.comment.exposition.payload.request;

import lombok.Getter;

@Getter
public class UpdateCommentRequest {
    private String id;
    private String content;
}
