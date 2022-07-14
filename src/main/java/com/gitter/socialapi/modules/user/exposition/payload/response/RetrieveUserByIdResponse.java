package com.gitter.socialapi.modules.user.exposition.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RetrieveUserByIdResponse {
    private String id;
    private String username;
    private Integer numberOfFollowers;
    private Integer numberOfFollows;
    private List<TeamUserResponse> teams;
}


