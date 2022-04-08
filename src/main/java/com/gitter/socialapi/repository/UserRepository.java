package com.gitter.socialapi.repository;

import com.gitter.socialapi.model.UserEntity;
import org.neo4j.driver.internal.shaded.reactor.core.publisher.Mono;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends ReactiveNeo4jRepository<UserEntity, Long> {

    Mono<UserEntity> findOneByEmail(String email);
}