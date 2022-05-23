package com.gitter.socialapi.publication.exposition.payload.request;

import lombok.Getter;

@Getter
public class RetrieveNewPublicationsRequest {
    private Long userId;
    private Integer pageNumber;
    private Integer numberPerPage;
}
