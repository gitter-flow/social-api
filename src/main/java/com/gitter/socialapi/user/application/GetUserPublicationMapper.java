package com.gitter.socialapi.user.application;

import com.gitter.socialapi.user.domain.User;
import com.gitter.socialapi.user.exposition.payload.response.GetUserPublicationsResponse;

import java.util.stream.Collectors;

public class GetUserPublicationMapper {
     private final String baseURL;

    public GetUserPublicationMapper(String baseURL) {
        this.baseURL = baseURL;
    }

    public GetUserPublicationsResponse getResponse(User user) {
        System.out.println(user.getPublications());
         return new GetUserPublicationsResponse(
           user.getPublications().stream().map(
                   p -> String.format("%s/publications/%d", "", p.getId())
           ).collect(Collectors.toList())
         );
     }
}
