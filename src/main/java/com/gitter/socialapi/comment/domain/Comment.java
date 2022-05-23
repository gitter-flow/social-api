package com.gitter.socialapi.comment.domain;

import com.gitter.socialapi.publication.domain.Publication;
import com.gitter.socialapi.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "comment")
public class Comment {
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
    @Cascade(value={org.hibernate.annotations.CascadeType.ALL})
    private List<User> likedBy = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt = new Date();

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    public Comment(User user, Publication publication, String content) {
        this.user = user;
        this.publication = publication;
        this.content = content;
    }
}
