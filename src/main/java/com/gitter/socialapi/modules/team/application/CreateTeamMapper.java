package com.gitter.socialapi.modules.team.application;

import com.gitter.socialapi.modules.team.domain.Team;
import com.gitter.socialapi.modules.team.exposition.payload.request.CreateTeamRequest;
import com.gitter.socialapi.modules.team.exposition.payload.response.CreateTeamResponse;
import com.gitter.socialapi.modules.user.domain.User;

import java.util.ArrayList;
import java.util.List;

public class CreateTeamMapper {
    
    public static Team toEntityTeam(CreateTeamRequest createTeamRequest, User user) {
        ArrayList<User> users =  new ArrayList<>();
        users.add(user);
        return new Team(
                createTeamRequest.getTeamName(),
                users
        );
    }
    
    public static CreateTeamResponse toResponse(Team team) {
        return new CreateTeamResponse(team.getId());
    }
}
