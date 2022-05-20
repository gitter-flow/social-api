package com.gitter.socialapi.user.exposition.payload.response;

import com.gitter.socialapi.user.domain.User;
import lombok.Getter;

@Getter
public class CreateUserResponse {
    private String userId;
    private CreateUserResponse(String userId) {
        this.userId = userId;
    }
    public static CreateUserResponse of(User user) {
        return new CreateUserResponse(user.getId().toString());
    }
    
}
