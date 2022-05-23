package com.gitter.socialapi.publication.exposition.payload.response;

import lombok.Getter;

import java.util.Set;

@Getter
public class RetrievePublicationResponse {
    private String userURI;
    private String content;
    private String codeURI;
    private String sharedPublicationURI;
    private String parentPublicationURI;
    private Set<String> likedByURIs;

    private RetrievePublicationResponse(String userURI, String content, String codeURI, String sharedPublicationURI, String parentPublicationURI, Set<String> likedByURIs) {
        this.userURI = userURI;
        this.content = content;
        this.codeURI = codeURI;
        this.sharedPublicationURI = sharedPublicationURI;
        this.parentPublicationURI = parentPublicationURI;
        this.likedByURIs = likedByURIs;
    }
    public static RetrievePublicationResponse of(String userURI, String content, String codeURI, String sharedPublicationURI, String parentPublicationURI, Set<String> likedByURIs) {
        return new RetrievePublicationResponse(userURI, content,codeURI, sharedPublicationURI, parentPublicationURI, likedByURIs);
    }
    
}
