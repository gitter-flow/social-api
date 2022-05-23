package com.gitter.socialapi.comment.exposition.payload.request;

import lombok.Getter;

@Getter
public class RetrievePublicationCommentsRequest {
    private Long id;
    private Integer pageNumber;
    private Integer numberPerPages;
}
