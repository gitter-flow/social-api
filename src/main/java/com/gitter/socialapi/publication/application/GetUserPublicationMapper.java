package com.gitter.socialapi.publication.application;

import com.gitter.socialapi.publication.domain.Publication;
import com.gitter.socialapi.user.domain.User;
import com.gitter.socialapi.publication.exposition.payload.response.GetUserPublicationsResponse;

import java.util.List;
import java.util.stream.Collectors;

public class GetUserPublicationMapper {
     private final String baseURL;

    public GetUserPublicationMapper(String baseURL) {
        this.baseURL = baseURL;
    }

    public GetUserPublicationsResponse getResponse(List<Publication> publicationList) {
         return new GetUserPublicationsResponse(
                 publicationList.stream().map(
                   p -> String.format("%s/publications/%d", baseURL, p.getId())
           ).collect(Collectors.toList())
         );
     }
}
