package com.gitter.socialapi.modules.user.exposition.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.criteria.CriteriaBuilder;

@Getter
@AllArgsConstructor
public class RetrieveUserFollowersRequest {
    private String userId;
    private Integer pageNumber;
    private Integer numberPerPage;
}
