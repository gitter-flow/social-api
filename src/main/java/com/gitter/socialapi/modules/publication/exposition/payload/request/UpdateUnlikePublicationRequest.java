package com.gitter.socialapi.modules.publication.exposition.payload.request;

import lombok.Getter;

@Getter
public class UpdateUnlikePublicationRequest {
    private String publicationId ;
    private String userId;
}
