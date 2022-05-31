package com.gitter.socialapi.publication.application;

import com.gitter.socialapi.publication.domain.Publication;
import com.gitter.socialapi.publication.exposition.payload.response.RetrieveUserPublicationsResponse;

import java.util.List;
import java.util.stream.Collectors;

public class RetrieveUserPublicationMapper {
     private final String baseURL;

    public RetrieveUserPublicationMapper(String baseURL) {
        this.baseURL = baseURL;
    }

    public RetrieveUserPublicationsResponse getResponse(List<Publication> publicationList) {
         return new RetrieveUserPublicationsResponse(
                 publicationList.stream().map(
                   p -> String.format("%s/publication/%d", baseURL, p.getId())
           ).collect(Collectors.toList())
         );
     }
}
