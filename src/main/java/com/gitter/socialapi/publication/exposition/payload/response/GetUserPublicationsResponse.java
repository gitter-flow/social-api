package com.gitter.socialapi.publication.exposition.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class GetUserPublicationsResponse {
    private List<String> publicationURIs;
}
