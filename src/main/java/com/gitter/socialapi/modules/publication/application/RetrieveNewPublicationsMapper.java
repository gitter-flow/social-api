package com.gitter.socialapi.modules.publication.application;

import com.gitter.socialapi.modules.publication.domain.Publication;
import com.gitter.socialapi.modules.publication.exposition.payload.response.RetrieveNewPublicationsResponse;
import com.gitter.socialapi.modules.user.domain.User;

import java.util.List;
import java.util.stream.Collectors;

public class RetrieveNewPublicationsMapper {
    public static List<RetrieveNewPublicationsResponse> toResponse(List<Publication> publicationList) {
        return publicationList.stream().map(
                        p -> new RetrieveNewPublicationsResponse(
                                p.getId(),
                                p.getUser().getUsername(),
                                p.getUser().getId(),
                                p.getContent(),
                                p.getCreatedAt().toString(),
                                p.getCode() != null ? p.getCode().getId() : null,
                                p.getSharedPublication() != null ? p.getSharedPublication().getId() : null,
                                p.getParentPublication() != null ? p.getParentPublication().getId() : null,
                                p.getLikedBy().stream().map(User::getId).collect(Collectors.toList())
                        )
                ).collect(Collectors.toList());
    }
}
