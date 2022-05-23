package com.gitter.socialapi.publication.application;

import com.gitter.socialapi.publication.domain.Publication;
import com.gitter.socialapi.publication.exposition.payload.response.RetrieveNewPublicationsResponse;

import java.util.List;
import java.util.stream.Collectors;

public class RetrieveNewPublicationsMapper {
    private final String baseURL;

    public RetrieveNewPublicationsMapper(String baseURL) {
        this.baseURL = baseURL;
    }
    
    public RetrieveNewPublicationsResponse toResponse(List<Publication> publicationList) {
        return new RetrieveNewPublicationsResponse(
                publicationList.stream().map(
                        p -> String.format("%s/publication/%d", baseURL, p.getId())
                ).collect(Collectors.toList())
        );
    }
}
