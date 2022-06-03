package com.gitter.socialapi.user.exposition.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RetrieveUserFollowsResponse {
    private String userId;
    private String username;
    private String userURL;
}
