package com.gitter.socialapi.modules.publication.application;

import com.gitter.socialapi.modules.publication.domain.Publication;
import com.gitter.socialapi.modules.user.domain.User;
import com.gitter.socialapi.modules.publication.exposition.payload.response.RetrievePublicationResponse;

import java.util.stream.Collectors;

public class RetrievePublicationMapper {
    public static RetrievePublicationResponse toResponse(Publication publication) {
        return RetrievePublicationResponse.of(
                publication.getUser().getId(),
                publication.getContent(),
                publication.getCode() != null? publication.getCode() .getId() : null,
                publication.getSharedPublication() != null ? publication.getSharedPublication().getId() : null,
                publication.getParentPublication() != null ? publication.getParentPublication().getId() : null,
                publication.getLikedBy().stream()
                        .map(User::getId)
                        .collect(Collectors.toList())
        );
    }
}
