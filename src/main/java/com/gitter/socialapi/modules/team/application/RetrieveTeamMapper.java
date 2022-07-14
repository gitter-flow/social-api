package com.gitter.socialapi.modules.team.application;

import com.gitter.socialapi.modules.team.domain.Team;
import com.gitter.socialapi.modules.team.exposition.payload.response.RetrieveTeamResponse;
import com.gitter.socialapi.modules.user.domain.User;

import java.util.stream.Collectors;

public class RetrieveTeamMapper {
    
    public static RetrieveTeamResponse toResponse(Team team) {
        return new RetrieveTeamResponse(
                team.getName(),
                team.getMembers().stream().map(
                        User::getId
                ).collect(Collectors.toList())
        );
    }
}
