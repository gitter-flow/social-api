package com.gitter.socialapi.user.application;

import com.gitter.socialapi.user.domain.User;
import com.gitter.socialapi.user.exposition.payload.response.RetrieveUserFollowersResponse;
import com.gitter.socialapi.user.exposition.payload.response.RetrieveUserFollowsResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class RetrieveUserFollowsMapper {
    
    private final String baseURL;

    public RetrieveUserFollowsMapper(String baseURL) {
        this.baseURL = baseURL;
    }

    public List<RetrieveUserFollowsResponse> toResponse(Page<User> users) {
        return users.getContent().stream().map(
                u -> new RetrieveUserFollowsResponse(
                        u.getId(),
                        u.getUsername(),
                        String.format("%s/user/%d", baseURL, u.getId())
                )
        ).collect(Collectors.toList());
    }
}
