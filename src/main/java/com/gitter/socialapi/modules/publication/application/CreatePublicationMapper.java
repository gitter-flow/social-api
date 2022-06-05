package com.gitter.socialapi.modules.publication.application;

import com.gitter.socialapi.modules.publication.domain.Publication;
import com.gitter.socialapi.modules.publication.exposition.payload.response.CreatePublicationResponse;

public final class CreatePublicationMapper {
    public static CreatePublicationResponse toResponse(Publication publication) {
        return CreatePublicationResponse.of(publication.getId());
    }
}
