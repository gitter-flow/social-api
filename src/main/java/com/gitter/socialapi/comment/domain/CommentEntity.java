package com.gitter.socialapi.comment.domain;

import com.gitter.socialapi.publication.domain.Publication;
import com.gitter.socialapi.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="publication_id")
    private Publication publication;

    private String content;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private List<User> likedBy = new ArrayList<>();
}
