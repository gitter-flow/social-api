package com.gitter.socialapi.comment.exposition.payload.response;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GetPublicationCommentsResponse {
    private String username;
    private Long userId;
    private String content;
    private List<String> likes;
}
