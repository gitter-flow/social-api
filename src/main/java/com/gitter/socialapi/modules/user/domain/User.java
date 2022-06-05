package com.gitter.socialapi.modules.user.domain;

import com.gitter.socialapi.modules.publication.domain.Publication;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="users")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    private String id = UUID.randomUUID().toString();
    
    @Column(name = "keycloak_id", nullable = false, unique = true)
    private String keycloakId;
    
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    private String firstName;
    private String lastName;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    private String description;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_follows",
            joinColumns = @JoinColumn( name = "id_user" ),
            inverseJoinColumns = @JoinColumn( name = "id_followed" )
    )
    private List<User> follows = new ArrayList<>();
    
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "follows", cascade = CascadeType.ALL)
    private List<User> followedBy = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Publication.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Publication> publications = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Publication> likedPublications = new HashSet<>();

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt = new Date();

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    public User(String keycloakId, String username, String description, String firstName, String lastName, String email) {
        this.keycloakId = keycloakId;
        this.username = username;
        this.description = description;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;        
    }
    public void addNewPublication(Publication publication) {
        this.publications.add(publication);
        publication.setUser(this);
    }
}
