package com.gitter.socialapi.comment.payload.request;

import lombok.Getter;

@Getter
public class UpdateCommentRequest {
    private String id;
    private String content;
}
