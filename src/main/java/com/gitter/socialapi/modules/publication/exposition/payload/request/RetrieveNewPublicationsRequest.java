package com.gitter.socialapi.modules.publication.exposition.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RetrieveNewPublicationsRequest {
    private String userId;
    private Integer pageNumber;
    private Integer numberPerPage;
}
