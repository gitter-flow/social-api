package com.gitter.socialapi.modules.team.application;

import com.gitter.socialapi.kernel.exceptions.InvalidParameterException;
import com.gitter.socialapi.kernel.exceptions.NoSuchEntityException;
import com.gitter.socialapi.modules.team.domain.Team;
import com.gitter.socialapi.modules.team.exposition.payload.request.*;
import com.gitter.socialapi.modules.team.exposition.payload.response.CreateTeamResponse;
import com.gitter.socialapi.modules.team.exposition.payload.response.RetrieveTeamResponse;
import com.gitter.socialapi.modules.team.infrastructure.TeamRepository;
import com.gitter.socialapi.modules.user.application.UserService;
import com.gitter.socialapi.modules.user.domain.User;
import com.gitter.socialapi.modules.user.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class TeamService {

    private final TeamRepository teamRepository;
    
    private final UserService userService;
    private final String baseURL;

    @Autowired
    public TeamService(TeamRepository teamRepository,
                       UserRepository userRepository, UserService userService, @Value("${application.url}") String baseURL
                              ) {
        this.teamRepository = teamRepository;
        this.userService = userService;
        this.baseURL = baseURL;
    }
    
    public Team getTeamEntityById(String idStr) {
        Optional<Team> team = teamRepository.findById(idStr);
        if(team.isEmpty()){
            throw NoSuchEntityException.withId(Team.class.getSimpleName(), idStr);
        }
        return team.get();
    }
    
    public RetrieveTeamResponse getTeamResponseByID(String id) {
        Team team = getTeamEntityById(id);
        return RetrieveTeamMapper.toResponse(team);
    }
    
    public CreateTeamResponse createTeam(CreateTeamRequest createRequest) throws InvalidParameterException {
        User user = userService.getUserFromStringId(createRequest.getUserId());
        Team team = CreateTeamMapper.toEntityTeam(createRequest, user);
        teamRepository.save(team);
        return CreateTeamMapper.toResponse(team);
    }
    
    public void joinTeam(JoinTeamRequest joinRequest) throws InvalidParameterException {
        System.out.println(joinRequest.getUserId());
        User user = userService.getUserFromStringId(joinRequest.getUserId());
        Team team = getTeamEntityById(joinRequest.getTeamId());
        if(team.getMembers().stream().anyMatch(u -> Objects.equals(u.getId(), joinRequest.getUserId()))) return;
        team.getMembers().add(user);
        teamRepository.save(team);
    }

    public void leaveTeam(LeaveTeamRequest leaveTeamRequest) throws InvalidParameterException {
        Team team = getTeamEntityById(leaveTeamRequest.getTeamId());
        team.setMembers(
                team.getMembers().stream().filter(
                        u -> !Objects.equals(u.getId(), leaveTeamRequest.getUserId())
                ).collect(Collectors.toList())
        );
        teamRepository.save(team);
    }

}
