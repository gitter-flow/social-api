package com.gitter.socialapi.user.domain;

import com.gitter.socialapi.publication.domain.PublicationEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String keycloakId;

    private String username;
    
    private String firstName;
    
    private String lastName;
    
    private String email;
    
    private String description;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinTable(
            name = "user_follows",
            joinColumns = @JoinColumn( name = "id_user" ),
            inverseJoinColumns = @JoinColumn( name = "id_followed" )
    )
    private List<User> follows = new ArrayList<>();
    
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinTable(
            name = "user_followed_by",
            joinColumns = @JoinColumn( name = "id_user" ),
            inverseJoinColumns = @JoinColumn( name = "id_follower" )
    )
    private List<User> followedBy = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    private List<PublicationEntity> publications = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private List<PublicationEntity> likedPublications = new ArrayList<>();
    
    public User(String keycloakId, String description, String firstName, String lastName, String email) {
        this.keycloakId = keycloakId;
        this.description = description;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;        
    }
}
