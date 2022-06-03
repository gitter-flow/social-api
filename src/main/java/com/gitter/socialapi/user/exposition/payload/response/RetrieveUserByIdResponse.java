package com.gitter.socialapi.user.exposition.payload.response;

import lombok.Getter;

@Getter
public class RetrieveUserByIdResponse {
    private String id;
    private String keycloakId;
    private String username;
    private Integer numberOfFollowers;
    private Integer numberOfFollows;

    private RetrieveUserByIdResponse(String id, String keycloakId, String username, Integer numberOfFollowers, Integer numberOfFollows) {
        this.id = id;
        this.keycloakId = keycloakId;
        this.username = username;
        this.numberOfFollowers = numberOfFollowers;
        this.numberOfFollows = numberOfFollows;
    }
    
    public static RetrieveUserByIdResponse of(String id, String keycloakId, String username, Integer numberOfFollowers, Integer numberOfFollows) {
        return new RetrieveUserByIdResponse(id, keycloakId, username, numberOfFollowers, numberOfFollows);
    }
}
