package com.gitter.socialapi.repository;

import com.gitter.socialapi.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findAll();
    Optional<UserEntity> findById(Long id);
}