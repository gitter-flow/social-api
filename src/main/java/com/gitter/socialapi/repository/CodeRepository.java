package com.gitter.socialapi.repository;

import com.gitter.socialapi.model.CodeEntity;
import com.gitter.socialapi.model.CommentaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeRepository extends JpaRepository<CodeEntity, Long> {

    List<CodeEntity> findAll();
}