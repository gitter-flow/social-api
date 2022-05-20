package com.gitter.socialapi.user.exposition.payload.response;

import lombok.Getter;

@Getter
public class RetrieveUserByIdResponse {
    private Long id;
    private String keycloakId;
    private String username;
    private Integer numberOfFollowers;
    private Integer numberOfFollows;

    private RetrieveUserByIdResponse(Long id, String keycloakId, String username, Integer numberOfFollowers, Integer numberOfFollows) {
        this.id = id;
        this.keycloakId = keycloakId;
        this.username = username;
        this.numberOfFollowers = numberOfFollowers;
        this.numberOfFollows = numberOfFollows;
    }
    
    public static RetrieveUserByIdResponse of(Long id, String keycloakId, String username, Integer numberOfFollowers, Integer numberOfFollows) {
        return new RetrieveUserByIdResponse(id, keycloakId, username, numberOfFollowers, numberOfFollows);
    }
}
