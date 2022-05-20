package com.gitter.socialapi.publication.application;

import com.gitter.socialapi.publication.domain.Publication;
import com.gitter.socialapi.publication.exposition.payload.response.GetPublicationResponse;

import java.util.stream.Collectors;

public class GetPublicationMapper {
    private final String baseURL;

    public GetPublicationMapper(String baseURL) {
        this.baseURL = baseURL;
    }

    public GetPublicationResponse toResponse(Publication publication) {
        String codeURI = null, sharedPublicationURI = null, parentPublicationURI = null;
        if(publication.getCode() != null) {
            codeURI = String.format("%s/code/%d", baseURL, publication.getCode().getId());
        }
        if(publication.getSharedPublication() != null) {
            sharedPublicationURI =  String.format("%s/publication/%d", baseURL, publication.getSharedPublication().getId());
        } else if(publication.getParentPublication() != null) {
            parentPublicationURI = String.format("%s/publication/%d", baseURL, publication.getCode().getId());
        }
        return GetPublicationResponse.of(
                String.format("%s/user/%d", baseURL, publication.getUser().getId()),
                publication.getContent(),
                codeURI,
                sharedPublicationURI,
                parentPublicationURI,
                publication.getLikedBy().stream()
                        .map(u -> String.format("%s/user/%d", baseURL, u.getId()))
                        .collect(Collectors.toSet())
        );
    }
}
