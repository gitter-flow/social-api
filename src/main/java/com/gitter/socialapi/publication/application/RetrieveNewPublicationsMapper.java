package com.gitter.socialapi.publication.application;

import com.gitter.socialapi.publication.domain.Publication;
import com.gitter.socialapi.publication.exposition.payload.response.RetrieveNewPublicationsResponse;
import com.gitter.socialapi.user.domain.User;

import java.util.List;
import java.util.stream.Collectors;

public class RetrieveNewPublicationsMapper {
    public static List<RetrieveNewPublicationsResponse> toResponse(List<Publication> publicationList) {
        return publicationList.stream().map(
                        p -> new RetrieveNewPublicationsResponse(
                                p.getUser().getUsername(),
                                p.getUser().getId(),
                                p.getContent(),
                                p.getCode() != null ? p.getCode().getId() : null,
                                p.getSharedPublication() != null ? p.getSharedPublication().getId() : null,
                                p.getParentPublication() != null ? p.getParentPublication().getId() : null,
                                p.getLikedBy().stream().map(User::getId).collect(Collectors.toList())
                        )
                ).collect(Collectors.toList());
    }
}
