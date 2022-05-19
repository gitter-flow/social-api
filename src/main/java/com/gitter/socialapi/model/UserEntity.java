package com.gitter.socialapi.model;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Node("User")
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Property("firstName")
    private String firstName;

    @Property("lastName")
    private String lastName;

    @Property("email")
    private String email;

    @Property("password")
    private String password;

    @Relationship(type = "DIRECTED", direction = Relationship.Direction.INCOMING)
    private List<UserEntity> follower = new ArrayList<>();

    @Relationship(type = "DIRECTED", direction = Relationship.Direction.INCOMING)
    private List<UserEntity> follow = new ArrayList<>();


}
