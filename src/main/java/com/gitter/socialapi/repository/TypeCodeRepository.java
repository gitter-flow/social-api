package com.gitter.socialapi.repository;

import com.gitter.socialapi.model.TypeCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeCodeRepository extends JpaRepository<TypeCodeEntity, Long> {

    List<TypeCodeEntity> findAll();
}