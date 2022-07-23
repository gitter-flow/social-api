package com.gitter.socialapi.modules.publication.infrastructure;

import com.gitter.socialapi.modules.publication.domain.Publication;
import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, String> {
    @Query(
            value = "select p from publication p where p.user.id = :id order by p.createdAt desc"
    )
    List<Publication> selectWhereUserIdEquals(@Param("id") String id, Pageable pageable);

    @Query(
            value = """
                select p from publication p
                join p.user.followedBy f
                where f.id in :id
                order by p.createdAt desc
                """
    )
    List<Publication> selectWhereUserFollows(@Param("id") String id, Pageable pageable);

    @Modifying
    @Query(
            value = """
                delete from publication p
                where p.id = :id
                """
    )
    void deleteById(@Param("id") @NonNull String id);
    
    @Query(
            value = """
                select p from publication p
                order by p.createdAt desc
                """
    )
    List<Publication> findAllOrderByCreatedAt(Pageable pageable);
    
}