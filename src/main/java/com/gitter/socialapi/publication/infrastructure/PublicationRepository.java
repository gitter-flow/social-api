package com.gitter.socialapi.publication;

import com.gitter.socialapi.publication.PublicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<PublicationEntity, Long> {

    List<PublicationEntity> findAll();
}