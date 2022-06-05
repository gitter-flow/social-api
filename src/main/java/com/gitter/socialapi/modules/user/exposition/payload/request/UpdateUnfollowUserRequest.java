package com.gitter.socialapi.modules.user.exposition.payload.request;

import lombok.Getter;

@Getter
public class UpdateUnfollowUserRequest {
    private String userId;
    private String userToUnfollowId;
}
