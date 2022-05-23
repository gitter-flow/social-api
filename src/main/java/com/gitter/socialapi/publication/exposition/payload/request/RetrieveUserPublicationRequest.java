package com.gitter.socialapi.publication.exposition.payload.request;

import lombok.Getter;

@Getter
public class RetrieveUserPublicationRequest {
    private String id;
    private Integer pageNumber;
    private Integer numberPerPage;
}
