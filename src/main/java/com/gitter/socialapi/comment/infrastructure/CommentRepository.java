package com.gitter.socialapi.comment.infrastructure;

import com.gitter.socialapi.comment.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    @Query(
            value = "select c from comment c where c.publication.id = :id"
    )
    Page<Comment> selectWherePublicationIdEquals(@Param("id") Long id, Pageable pageable);
}