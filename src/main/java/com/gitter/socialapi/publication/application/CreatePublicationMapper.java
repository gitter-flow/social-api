package com.gitter.socialapi.publication.application;

import com.gitter.socialapi.publication.domain.Publication;
import com.gitter.socialapi.publication.exposition.payload.request.CreatePublicationRequest;
import com.gitter.socialapi.publication.exposition.payload.response.CreatePublicationResponse;
import com.gitter.socialapi.user.domain.User;
import org.springframework.stereotype.Service;

public final class CreatePublicationMapper {
    public static CreatePublicationResponse toResponse(Publication publication) {
        return CreatePublicationResponse.of(publication.getId());
    }
}
