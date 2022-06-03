package com.gitter.socialapi.publication.application;

import com.gitter.socialapi.publication.domain.Publication;
import com.gitter.socialapi.publication.exposition.payload.response.RetrieveAllPublicationsResponse;
import com.gitter.socialapi.publication.exposition.payload.response.RetrieveUserPublicationsResponse;
import com.gitter.socialapi.user.domain.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class RetrieveAllPublicationsMapper {
    public static List<RetrieveAllPublicationsResponse> toResponse(Page<Publication> publications) {
        return publications.get().map(
                        p -> new RetrieveAllPublicationsResponse(
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
