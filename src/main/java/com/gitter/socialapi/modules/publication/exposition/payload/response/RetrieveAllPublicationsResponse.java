package com.gitter.socialapi.modules.publication.exposition.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RetrieveAllPublicationsResponse {
    private String id;
    private String username;
    private String userId;
    private String content;
    private String createdAt;
    private String codeId;
    private String sharedPublicationId;
    private String parentPublicationId;
    private List<String> likes;
}
