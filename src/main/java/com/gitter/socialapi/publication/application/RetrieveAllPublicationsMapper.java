package com.gitter.socialapi.publication.application;

import com.gitter.socialapi.publication.domain.Publication;
import com.gitter.socialapi.publication.exposition.payload.response.RetrieveAllPublicationsResponse;
import com.gitter.socialapi.publication.exposition.payload.response.RetrieveUserPublicationsResponse;
import org.springframework.data.domain.Page;

import java.util.stream.Collectors;

public class RetrieveAllPublicationsMapper {
    private final String baseURL;
    public RetrieveAllPublicationsMapper(String baseURL) {
        this.baseURL = baseURL;
    }
    
    public RetrieveAllPublicationsResponse toResponse(Page<Publication> publications) {
        return new RetrieveAllPublicationsResponse(
                publications.get().map(
                        p -> String.format("%s/publication/%d", baseURL, p.getId())
                ).collect(Collectors.toList())
        );
    }
}
