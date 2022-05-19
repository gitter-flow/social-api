package com.gitter.socialapi.user.application;

import com.gitter.socialapi.user.domain.User;
import com.gitter.socialapi.user.exposition.payload.request.CreateUserRequest;
import com.gitter.socialapi.user.exposition.payload.response.CreateUserResponse;

public class CreateUserMapper {
    
    public static User getUser(CreateUserRequest createUserRequest) {
        return new User(
                createUserRequest.getKeycloakId(),
                createUserRequest.getDescription(),
                createUserRequest.getFirstName(),
                createUserRequest.getLastName(),
                createUserRequest.getEmail()
        );
    }
    public static CreateUserResponse getResponse(User user) {
        return CreateUserResponse.of(user);
    }
}
