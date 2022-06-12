package com.gitter.socialapi.modules.user.exposition.payload.response;

import lombok.Getter;

@Getter
public class RetrieveUserByIdResponse {
    private String id;
    private String username;
    private Integer numberOfFollowers;
    private Integer numberOfFollows;

    private RetrieveUserByIdResponse(String id,String username, Integer numberOfFollowers, Integer numberOfFollows) {
        this.id = id;
        this.username = username;
        this.numberOfFollowers = numberOfFollowers;
        this.numberOfFollows = numberOfFollows;
    }
    
    public static RetrieveUserByIdResponse of(String id, String username, Integer numberOfFollowers, Integer numberOfFollows) {
        return new RetrieveUserByIdResponse(id, username, numberOfFollowers, numberOfFollows);
    }
}
