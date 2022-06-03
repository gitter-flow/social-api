package com.gitter.socialapi.publication.exposition.payload.response;

import lombok.Getter;

@Getter
public class CreatePublicationResponse {
    private String id;

    private CreatePublicationResponse(String id) {
        this.id = id;
    }
    public static CreatePublicationResponse of(String id) {
        return new CreatePublicationResponse(id);
    }
}
