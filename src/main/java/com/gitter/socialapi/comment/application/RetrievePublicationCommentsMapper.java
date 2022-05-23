package com.gitter.socialapi.comment.application;

import com.gitter.socialapi.comment.domain.Comment;
import com.gitter.socialapi.comment.exposition.payload.response.RetrievePublicationCommentsResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class RetrievePublicationCommentsMapper {
    private final String baseURL;

    public RetrievePublicationCommentsMapper(String baseURL) {
        this.baseURL = baseURL;
    }

    public List<RetrievePublicationCommentsResponse> toResponse(Page<Comment> commentPage) {
        return commentPage.getContent().stream().map(
                c -> new RetrievePublicationCommentsResponse(
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
