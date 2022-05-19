package com.gitter.socialapi.user.application;

import com.gitter.socialapi.user.domain.User;
import com.gitter.socialapi.user.exposition.payload.request.UpdateUserRequest;
import com.gitter.socialapi.user.exposition.payload.response.RetrieveUserByIdResponse;

public class UpdateUserMapper {
    public static User getUser(UpdateUserRequest userRequest, User user ) {
        if (userRequest.getDescription() != null) {
            user.setDescription(userRequest.getDescription());
        }
        if (userRequest.getFirstName() != null) {
            user.setFirstName(userRequest.getFirstName());
        }
        if (userRequest.getLastName() != null) {
            user.setLastName(userRequest.getLastName());
        }
        if (userRequest.getEmail() != null) {
            user.setEmail(userRequest.getEmail());
        }
        if (userRequest.getDescription() != null) {
            user.setDescription(userRequest.getDescription());
        }
        return user;
    }
}
