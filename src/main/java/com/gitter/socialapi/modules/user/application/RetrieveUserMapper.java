package com.gitter.socialapi.modules.user.application;

import com.gitter.socialapi.modules.user.exposition.payload.response.RetrieveUserByIdResponse;
import com.gitter.socialapi.modules.user.domain.User;
import com.gitter.socialapi.modules.user.exposition.payload.response.TeamUserResponse;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RetrieveUserMapper {
    public static RetrieveUserByIdResponse toGetUserByIdResponse(User user) {
        return new RetrieveUserByIdResponse(
                user.getId(),
                user.getUsername(),
                user.getFollowedBy().size(),
                user.getFollows().size(),
                user.getTeams().stream().map(
                        t -> new TeamUserResponse(t.getId(), t.getName())
                ).collect(Collectors.toList())
        );
    }
}
