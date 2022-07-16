package com.gitter.socialapi.modules.user.application;

import com.gitter.socialapi.modules.user.domain.User;
import com.gitter.socialapi.modules.user.exposition.payload.response.SearchUserResponse;

import java.util.List;
import java.util.stream.Collectors;

public class SearchUserMapper {
    
    public static List<SearchUserResponse> toResponse(List<User> users) {
        return users.stream().map(
                u -> new SearchUserResponse(u.getId(), u.getUsername())
        ).collect(Collectors.toList());
    }
}
