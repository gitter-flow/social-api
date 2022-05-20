package com.gitter.socialapi.publication.exposition.payload.response;

import lombok.Getter;

@Getter
public class CreatePublicationResponse {
    private Long id;

    private CreatePublicationResponse(Long id) {
        this.id = id;
    }
    public static CreatePublicationResponse of(Long id) {
        return new CreatePublicationResponse(id);
    }
}
