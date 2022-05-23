package com.gitter.socialapi.comment.application;

import com.gitter.socialapi.comment.domain.Comment;
import com.gitter.socialapi.comment.exposition.payload.response.GetPublicationCommentsResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class GetPublicationCommentsMapper {
    private final String baseURL;

    public GetPublicationCommentsMapper(String baseURL) {
        this.baseURL = baseURL;
    }

    public List<GetPublicationCommentsResponse> toResponse(Page<Comment> commentPage) {
        return commentPage.getContent().stream().map(
                c -> new GetPublicationCommentsResponse(
                        c.getUser().getUsername(),
                        c.getUser().getId(),
                        c.getContent(),
                        c.getLikedBy().stream().map(
                                u -> String.format("%s/user/%d", baseURL, u.getId())
                        ).collect(Collectors.toList())
                )
        ).collect(Collectors.toList());
    }
}
