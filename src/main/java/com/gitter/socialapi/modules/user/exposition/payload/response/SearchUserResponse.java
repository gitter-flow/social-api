package com.gitter.socialapi.modules.user.exposition.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchUserResponse {
    private String userId;
    private String username;
}
