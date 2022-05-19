package com.gitter.socialapi.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findAll();

    List<CommentEntity> getCommentaryEntitiesByPublication_Id (Long id);
}