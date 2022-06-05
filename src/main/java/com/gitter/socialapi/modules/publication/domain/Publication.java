package com.gitter.socialapi.modules.publication.domain;

import com.gitter.socialapi.modules.code.domain.Code;
import com.gitter.socialapi.modules.comment.domain.Comment;
import com.gitter.socialapi.modules.user.domain.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "publication")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Publication {
    @Id
    @Column(name = "id", nullable = false)
    private String id = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    private String content;
    
    @Column(name = "disabled")
    private Boolean disabled = false;

    @OneToOne(mappedBy = "publication", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "code_id")
    private Code code;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
    @JoinColumn(name= "shared_publication", foreignKey = @javax.persistence.ForeignKey(name = "none"))
    private Publication sharedPublication;

    @ManyToOne(fetch = FetchType.LAZY,  cascade = { CascadeType.ALL, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
    @JoinColumn(name= "parent_publication", foreignKey = @javax.persistence.ForeignKey(name = "none"))
    private Publication parentPublication;
    
    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> likedBy;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Publication.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt = new Date();

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}
