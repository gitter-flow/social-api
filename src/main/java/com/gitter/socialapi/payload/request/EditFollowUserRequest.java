package com.gitter.socialapi.payload.request;

import lombok.Getter;

@Getter
public class EditFollowUserRequest {
    private String userId;
    private String userToFollowId;
}
