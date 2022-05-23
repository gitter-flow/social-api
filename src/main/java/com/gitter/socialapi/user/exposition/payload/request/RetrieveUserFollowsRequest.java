package com.gitter.socialapi.user.exposition.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RetrieveUserFollowsRequest {
    private String userId;
    private Integer pageNumber;
    private Integer numberPerPage;
}
