package com.gitter.socialapi.modules.comment.application;

import com.gitter.socialapi.modules.comment.domain.Comment;
import com.gitter.socialapi.modules.comment.exposition.payload.response.RetrievePublicationCommentsResponse;
import com.gitter.socialapi.modules.user.domain.User;

import java.util.List;
import java.util.stream.Collectors;

public class RetrievePublicationCommentsMapper {
    public static List<RetrievePublicationCommentsResponse> toResponse(List<Comment> commentPage) {
        return commentPage.stream().map(
                c -> new RetrievePublicationCommentsResponse(
                        c.getUser().getUsername(),
                        c.getUser().getId(),
                        c.getContent(),
                        c.getLikedBy().stream().map(User::getId).collect(Collectors.toList())
                )
        ).collect(Collectors.toList());
    }
}
