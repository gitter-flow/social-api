package com.gitter.socialapi.modules.team.exposition;

import com.gitter.socialapi.kernel.exceptions.InvalidParameterException;
import com.gitter.socialapi.modules.team.application.TeamService;
import com.gitter.socialapi.modules.team.exposition.payload.request.*;
import com.gitter.socialapi.modules.team.exposition.payload.response.*;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/team",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class TeamController {
    private final TeamService teamService;
    
    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }
    
    @PostMapping
    @PreAuthorize("@authService.tokenIsValidForUserWithId(#createRequest.userId, #authentication)")
    public ResponseEntity<CreateTeamResponse> createTeam(@RequestBody CreateTeamRequest createRequest, KeycloakAuthenticationToken authentication) throws InvalidParameterException {
        CreateTeamResponse response = teamService.createTeam(createRequest);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<RetrieveTeamResponse> getTeamById(@PathVariable String id) throws InvalidParameterException {
        return ResponseEntity.ok(teamService.getTeamResponseByID(id));
    }
    
    @PutMapping("/join")
    @PreAuthorize("@authService.tokenIsValidForUserWithId(#joinTeamRequest.userId, #authentication)")
    public ResponseEntity<String> joinTeam(@RequestBody JoinTeamRequest joinTeamRequest, KeycloakAuthenticationToken authentication) throws InvalidParameterException {
        teamService.joinTeam(joinTeamRequest);
        return ResponseEntity.ok(String.format("User with id %s joined team with id %s", joinTeamRequest.getUserId(), joinTeamRequest.getTeamId()));
    }
    
    @PutMapping("/leave")
    @PreAuthorize("@authService.tokenIsValidForUserWithId(#leaveTeamRequest.userId, #authentication)")
    public ResponseEntity<String> leaveTeam(@RequestBody LeaveTeamRequest leaveTeamRequest , KeycloakAuthenticationToken authentication) throws InvalidParameterException {
        teamService.leaveTeam(leaveTeamRequest);
        return ResponseEntity.ok(String.format("User with id %s joined team with id %s", leaveTeamRequest.getUserId(), leaveTeamRequest.getTeamId()));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("@authService.tokenIsValidForTeamWithId(#id, #authentication)")
    public ResponseEntity<String> deleteTeam(@PathVariable String id, KeycloakAuthenticationToken authentication) {
        teamService.deleteTeam(id);
        return ResponseEntity.ok(String.format("Team with ID %s has been deleted", id));
    }
}
