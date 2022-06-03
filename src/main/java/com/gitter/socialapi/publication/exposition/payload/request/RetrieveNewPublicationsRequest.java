package com.gitter.socialapi.publication.exposition.payload.request;

import lombok.Getter;

@Getter
public class RetrieveNewPublicationsRequest {
    private String userId;
    private Integer pageNumber;
    private Integer numberPerPage;
}
