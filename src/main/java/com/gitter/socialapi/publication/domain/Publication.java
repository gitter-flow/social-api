package com.gitter.socialapi.publication.domain;

import com.gitter.socialapi.code.domain.Code;
import com.gitter.socialapi.comment.domain.Comment;
import com.gitter.socialapi.user.domain.User;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity(name = "publication")
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    private String content;
    
    @Column(name = "disabled")
    private Boolean disabled = false;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="code_id")
    @Nullable
    private Code code;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
    @JoinColumn(name= "shared_publication", foreignKey = @javax.persistence.ForeignKey(name = "none"))
    private Publication sharedPublication;

    @ManyToOne(fetch = FetchType.LAZY,  cascade = { CascadeType.ALL, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
    @JoinColumn(name= "parent_publication", foreignKey = @javax.persistence.ForeignKey(name = "none"))
    private Publication parentPublication;
    
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<User> likedBy;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Publication.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt = new Date();

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}
