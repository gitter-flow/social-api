package com.gitter.socialapi.modules.publication.exposition.payload.response;

import lombok.Getter;

import java.util.List;

@Getter
public class RetrievePublicationResponse {
    private String userId;
    private String content;
    private String codeId;
    private String sharedPublicationId;
    private String parentPublicationId;
    private List<String> likes;

    private RetrievePublicationResponse(String userId, String content, String codeId, String sharedPublicationId, String parentPublicationId, List<String> likes) {
        this.userId = userId;
        this.content = content;
        this.codeId = codeId;
        this.sharedPublicationId = sharedPublicationId;
        this.parentPublicationId = parentPublicationId;
        this.likes = likes;
    }
    public static RetrievePublicationResponse of(String userId, String content, String codeId, String sharedPublicationId, String parentPublicationId, List<String> likes) {
        return new RetrievePublicationResponse(userId, content, codeId, sharedPublicationId, parentPublicationId, likes);
    }
    
}
