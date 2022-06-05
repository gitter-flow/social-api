package com.gitter.socialapi.modules.publication.exposition.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RetrieveUserPublicationRequest {
    private String id;
    private Integer pageNumber;
    private Integer numberPerPage;
}
