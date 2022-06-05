package com.gitter.socialapi.modules.comment.exposition.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RetrievePublicationCommentsRequest {
    private String publicationId;
    private Integer pageNumber;
    private Integer numberPerPages;
}
