package com.gitter.socialapi.modules.user.exposition.payload.response;

import com.gitter.socialapi.modules.user.domain.User;
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
