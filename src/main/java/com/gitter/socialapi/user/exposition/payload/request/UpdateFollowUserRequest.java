package com.gitter.socialapi.user.exposition.payload.request;

import lombok.Getter;

@Getter
public class UpdateFollowUserRequest {
    private String userId;
    private String userToFollowId;
}
