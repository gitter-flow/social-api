package com.gitter.socialapi.user.application;

import com.gitter.socialapi.user.domain.User;
import com.gitter.socialapi.user.exposition.payload.response.RetrieveUserByIdResponse;
import org.springframework.stereotype.Component;

@Component
public class RetrieveUserMapper {
    public static RetrieveUserByIdResponse toGetUserByIdResponse(User user) {
        return RetrieveUserByIdResponse.of(
                user.getId(),
                user.getKeycloakId(), 
                user.getUsername(),
                user.getFollowedBy().size(),
                user.getFollows().size());
    }
}
