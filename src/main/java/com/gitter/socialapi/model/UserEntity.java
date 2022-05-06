package com.gitter.socialapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private Long keycloakId;
    private String username;

    @OneToMany(fetch = FetchType.LAZY)
    private List<UserEntity> follower = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    private List<UserEntity> followed = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    private List<PublicationEntity> publications = new ArrayList<>();

}
