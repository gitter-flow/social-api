package com.gitter.socialapi.modules.user.exposition.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RetrieveUserFollowersResponse {
    private String userId;
    private String username;
    private String userURL;
}
