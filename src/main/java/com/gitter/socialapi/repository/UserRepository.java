package com.gitter.socialapi.repository;

import com.gitter.socialapi.model.UserEntity;
import org.neo4j.driver.internal.shaded.reactor.core.publisher.Mono;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends Neo4jRepository<UserEntity, Long> {

    List<UserEntity> findAll();
}