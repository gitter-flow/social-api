package com.gitter.socialapi.modules.user.application;

import com.gitter.socialapi.modules.user.exposition.payload.request.CreateUserRequest;
import com.gitter.socialapi.modules.user.exposition.payload.response.CreateUserResponse;
import com.gitter.socialapi.modules.user.domain.User;

public class CreateUserMapper {
    
    public static User getUser(CreateUserRequest createUserRequest) {
        return new User(
                createUserRequest.getKeycloakId(),
                createUserRequest.getUsername(),
                createUserRequest.getFirstName(),
                createUserRequest.getLastName(),
                createUserRequest.getEmail()
        );
    }
    public static CreateUserResponse getResponse(User user) {
        return CreateUserResponse.of(user);
    }
}
