package com.gitter.socialapi.modules.team.exposition.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LeaveTeamRequest {
    private String userId;
    private String teamId;
}
