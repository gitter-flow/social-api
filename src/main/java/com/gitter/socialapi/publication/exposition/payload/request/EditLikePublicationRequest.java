package com.gitter.socialapi.publication.exposition.payload.request;

import lombok.Getter;

@Getter
public class EditLikePublicationRequest {
    private String publicationId ;
    private String userId;

}
