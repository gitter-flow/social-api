package com.gitter.socialapi.modules.user.application;

import com.gitter.socialapi.modules.user.exposition.payload.response.RetrieveUserByIdResponse;
import com.gitter.socialapi.modules.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class RetrieveUserMapper {
    public static RetrieveUserByIdResponse toGetUserByIdResponse(User user) {
        return RetrieveUserByIdResponse.of(
                user.getId(),
                user.getUsername(),
                user.getFollowedBy().size(),
                user.getFollows().size());
    }
}
