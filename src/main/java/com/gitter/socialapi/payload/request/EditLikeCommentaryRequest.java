package com.gitter.socialapi.payload.request;

import lombok.Getter;

@Getter
public class EditLikeCommentaryRequest {
    private String commentaryId ;
    private String userId;
}
