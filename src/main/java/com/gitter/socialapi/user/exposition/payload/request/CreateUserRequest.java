package com.gitter.socialapi.user.exposition.payload.request;

import lombok.Getter;

@Getter
public class CreateUserRequest {
     private String keycloakId;
     private String username;
     private String firstName;
     private String lastName;
     private String email;
     private String description;
}
