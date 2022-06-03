package com.gitter.socialapi.comment.application;

import com.gitter.socialapi.comment.domain.Comment;
import com.gitter.socialapi.comment.exposition.payload.response.RetrievePublicationCommentsResponse;
import com.gitter.socialapi.user.domain.User;
import org.springframework.data.domain.Page;

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
