package com.gitter.socialapi.repository;

import com.gitter.socialapi.model.CommentaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentaryRepository extends JpaRepository<CommentaryEntity, Long> {

    List<CommentaryEntity> findAll();

    List<CommentaryEntity> getCommentaryEntitiesByPublication_Id (Long id);
}