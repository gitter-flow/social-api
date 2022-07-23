package com.gitter.socialapi.modules.auth.exposition.payload.request;


import lombok.Getter;

@Getter
public class LoginRequest {
    String username;
    String password;
}
