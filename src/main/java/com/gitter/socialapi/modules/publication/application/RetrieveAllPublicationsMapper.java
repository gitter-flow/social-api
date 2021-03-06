package com.gitter.socialapi.modules.publication.application;

import com.gitter.socialapi.modules.publication.domain.Publication;
import com.gitter.socialapi.modules.publication.exposition.payload.response.RetrieveAllPublicationsResponse;
import com.gitter.socialapi.modules.user.domain.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class RetrieveAllPublicationsMapper {
    public static List<RetrieveAllPublicationsResponse> toResponse(List<Publication> publications) {
        return publications.stream().map(
                        p -> new RetrieveAllPublicationsResponse(
                                p.getId(),
                                p.getUser().getUsername(),
                                p.getUser().getId(),
                                p.getContent(),
                                p.getCreatedAt().toString(),
                                p.getCode() != null ? p.getCode().getId() : null,
                                p.getSharedPublication() != null ? p.getSharedPublication().getId() : null,
                                p.getParentPublication() != null ? p.getParentPublication().getId() : null,
                                p.getParentPublication() != null ? p.getParentPublication().getUser().getUsername() : null,
                                p.getLikedBy().stream().map(User::getId).collect(Collectors.toList())
                        )
                ).collect(Collectors.toList());
    }
}
