package com.gitter.socialapi.payload.request;

import lombok.Getter;

@Getter
public class EditLikePublicationRequest {
    private String publicationId ;
    private String userId;

}
