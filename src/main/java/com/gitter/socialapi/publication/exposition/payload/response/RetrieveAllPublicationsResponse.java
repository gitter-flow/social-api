package com.gitter.socialapi.publication.exposition.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RetrieveAllPublicationsResponse {
    private List<String> publicationURIs;
}
