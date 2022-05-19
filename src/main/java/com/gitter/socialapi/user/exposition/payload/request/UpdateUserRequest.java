package com.gitter.socialapi.user.exposition.payload.request;

import lombok.Getter;

@Getter
public class UpdateUserRequest {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String description;
}
