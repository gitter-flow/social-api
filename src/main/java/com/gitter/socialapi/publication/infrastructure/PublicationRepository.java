package com.gitter.socialapi.publication.infrastructure;

import com.gitter.socialapi.publication.domain.PublicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<PublicationEntity, Long> {

    List<PublicationEntity> findAll();
}