package com.gitter.socialapi.publication.exposition.payload.request;

import lombok.Getter;

@Getter
public class RetrieveAllPublicationsRequest {
    private Integer pageNumber;
    private Integer numberPerPage;
}
