package com.gitter.socialapi.publication.application;

import com.gitter.socialapi.publication.domain.Publication;
import com.gitter.socialapi.publication.exposition.payload.response.RetrievePublicationResponse;
import com.gitter.socialapi.user.domain.User;

import java.util.stream.Collectors;

public class RetrievePublicationMapper {
    public static RetrievePublicationResponse toResponse(Publication publication) {

        return RetrievePublicationResponse.of(
                publication.getUser().getId(),
                publication.getContent(),
                publication.getCode().getId(),
                publication.getSharedPublication() != null ? publication.getSharedPublication().getId() : null,
                publication.getParentPublication() != null ? publication.getParentPublication().getId() : null,
                publication.getLikedBy().stream()
                        .map(User::getId)
                        .collect(Collectors.toList())
        );
    }
}
