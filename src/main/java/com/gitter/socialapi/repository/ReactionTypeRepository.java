package com.gitter.socialapi.repository;

import com.gitter.socialapi.model.ReactionTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactionTypeRepository extends JpaRepository<ReactionTypeEntity, Long> {

    List<ReactionTypeEntity> findAll();
}