package com.gitter.socialapi.modules.team.domain;

import com.gitter.socialapi.modules.code.domain.Code;
import com.gitter.socialapi.modules.comment.domain.Comment;
import com.gitter.socialapi.modules.publication.domain.Publication;
import com.gitter.socialapi.modules.user.domain.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "team")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Team {
    @Id
    @Column(name = "id", nullable = false)
    private String id = UUID.randomUUID().toString();
    
    @Column(name = "name", unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "team_owner", foreignKey = @javax.persistence.ForeignKey(name = "none"))
    private User owner;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "teams_members",
            joinColumns = @JoinColumn( name = "team_id" ),
            inverseJoinColumns = @JoinColumn( name = "user_id" )
    )
    private List<User> members = new ArrayList<>();
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt = new Date();

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    public Team(String name, User owner) {
        this.name = name;
        this.owner = owner;
    }
}
