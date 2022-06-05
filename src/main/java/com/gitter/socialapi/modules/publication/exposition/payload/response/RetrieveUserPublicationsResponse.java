package com.gitter.socialapi.modules.publication.exposition.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class RetrieveUserPublicationsResponse {
    private String username;
    private String userId;
    private String content;
    private String codeId;
    private String sharedPublicationId;
    private String parentPublicationId;
    private List<String> likes;
}
