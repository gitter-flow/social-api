package com.gitter.socialapi.modules.publication.application;

import com.gitter.socialapi.modules.publication.domain.Publication;
import com.gitter.socialapi.modules.user.domain.User;
import com.gitter.socialapi.modules.publication.exposition.payload.response.RetrievePublicationResponse;

import java.util.stream.Collectors;

public class RetrievePublicationMapper {
    public static RetrievePublicationResponse toResponse(Publication publication) {
        return new RetrievePublicationResponse(
                publication.getUser().getId(),
                publication.getUser().getUsername(),
                publication.getContent(),
                publication.getCreatedAt().toString(),
                publication.getCode() != null? publication.getCode() .getId() : null,
                publication.getSharedPublication() != null ? publication.getSharedPublication().getId() : null,
                publication.getParentPublication() != null ? publication.getParentPublication().getId() : null,
                publication.getParentPublication() != null ? publication.getParentPublication().getUser().getUsername() : null,
                publication.getLikedBy().stream()
                        .map(User::getId)
                        .collect(Collectors.toList())
        );
    }
}
