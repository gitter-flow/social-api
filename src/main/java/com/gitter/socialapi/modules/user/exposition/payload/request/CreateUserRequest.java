package com.gitter.socialapi.modules.user.exposition.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
     private  String keycloakId;
     private String username;
     private String firstName;
     private String lastName;
     private String email;
}
