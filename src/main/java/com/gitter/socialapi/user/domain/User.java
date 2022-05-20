package com.gitter.socialapi.user.domain;

import com.gitter.socialapi.publication.domain.Publication;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
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
    
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "follows")
    private Set<User> followedBy = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Publication> publications = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Publication> likedPublications = new HashSet<>();
    
    public User(String keycloakId, String username, String description, String firstName, String lastName, String email) {
        this.keycloakId = keycloakId;
        this.username = username;
        this.description = description;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;        
    }
}
