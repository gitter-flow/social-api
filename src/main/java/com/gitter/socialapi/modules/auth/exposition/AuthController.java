package com.gitter.socialapi.modules.auth.exposition;

import com.gitter.socialapi.modules.auth.config.KeycloakProvider;
import com.gitter.socialapi.modules.auth.exposition.payload.request.CreateUserRequest;
import com.gitter.socialapi.modules.auth.exposition.payload.request.LoginRequest;
import com.gitter.socialapi.modules.auth.exposition.payload.response.LoginResponse;
import com.gitter.socialapi.modules.auth.service.KeycloakAdminClientService;
import com.gitter.socialapi.modules.user.application.UserService;
import com.gitter.socialapi.modules.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    private final KeycloakAdminClientService kcAdminClient;

    private final KeycloakProvider kcProvider;
    
    private final UserService userService;

    public AuthController(KeycloakAdminClientService kcAdminClient, KeycloakProvider kcProvider, UserService userService) {
        this.kcAdminClient = kcAdminClient;
        this.kcProvider = kcProvider;
        this.userService = userService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createUserKeycloak(@RequestBody CreateUserRequest user) {
        Response createdResponse = kcAdminClient.createKeycloakUser(user);
        return ResponseEntity.status(createdResponse.getStatus()).build();

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@NotNull @RequestBody LoginRequest loginRequest) {
        Keycloak keycloak = kcProvider.newKeycloakBuilderWithPasswordCredentials(loginRequest.getUsername(), loginRequest.getPassword()).build();
        User user = userService.serchFromEmailOrUsername(loginRequest.getUsername());
        AccessTokenResponse accessTokenResponse = null;
        try {
            accessTokenResponse = keycloak.tokenManager().getAccessToken();
            return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(user.getId(), accessTokenResponse.getToken()));
        } catch (BadRequestException | NotAuthorizedException ex) {
            log.warn("Invalid account: ", ex);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new LoginResponse());
        }

    }
    
}
