package com.gitter.socialapi.publication.infrastructure;

import com.gitter.socialapi.publication.domain.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {
    @Query(
            value = "select p from publication p where p.user.id = :id"
    )
    List<Publication> selectWhereUserId(@Param("id") Long id);
}