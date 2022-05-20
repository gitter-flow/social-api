package com.gitter.socialapi.user.exposition.payload.request;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class CreateUserRequest {
     @NonNull
     private final String keycloakId;
     @NonNull
     private String username;
     
     private String firstName;
     private String lastName;
     private String email;
     private String description;

     public CreateUserRequest(@NonNull String keycloakId, @NonNull String username) {
          this.keycloakId = keycloakId;
          this.username = username;
     }
}
