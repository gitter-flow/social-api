package com.gitter.socialapi.comment.exposition.payload.request;

import lombok.Getter;

@Getter
public class EditLikeCommentaryRequest {
    private String commentaryId ;
    private String userId;
}
