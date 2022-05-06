package com.gitter.socialapi.repository;

import com.gitter.socialapi.model.ReactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactionRepository extends JpaRepository<ReactionEntity, Long> {

    List<ReactionEntity> findAll();
}