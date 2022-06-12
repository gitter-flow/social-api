package com.gitter.socialapi.modules.user.exposition.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class CreateUserRequest {
     private final String keycloakId;
     private String username;
     private String firstName;
     private String lastName;
     private String email;
}
