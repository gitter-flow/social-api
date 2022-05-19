package com.gitter.socialapi.user.exposition.payload.request;

import lombok.Getter;

@Getter
public class UpdateUnfollowUserRequest {
    private String userId;
    private String userToUnfollowId;
}
