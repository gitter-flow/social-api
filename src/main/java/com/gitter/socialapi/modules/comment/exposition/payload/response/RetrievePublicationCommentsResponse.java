package com.gitter.socialapi.modules.comment.exposition.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class RetrievePublicationCommentsResponse {
    private String username;
    private String userId;
    private String content;
    private List<String> likes;
}
