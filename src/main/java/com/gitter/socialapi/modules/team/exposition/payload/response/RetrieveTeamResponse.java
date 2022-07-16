package com.gitter.socialapi.modules.team.exposition.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RetrieveTeamResponse {
    private String name;
    private String ownerId;
    private List<String> memberId;
}
