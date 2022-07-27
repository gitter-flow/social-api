package com.gitter.socialapi.modules.publication.application;

import com.gitter.socialapi.modules.publication.exposition.payload.response.RetrieveUserPublicationsResponse;
import com.gitter.socialapi.modules.publication.domain.Publication;
import com.gitter.socialapi.modules.user.domain.User;

import java.util.List;
import java.util.stream.Collectors;

public class RetrieveUserPublicationMapper {
    public static List<RetrieveUserPublicationsResponse> getResponse(List<Publication> publicationList) {
         return publicationList.stream().map(
                   p -> new RetrieveUserPublicationsResponse(
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
