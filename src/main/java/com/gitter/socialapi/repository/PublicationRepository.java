package com.gitter.socialapi.repository;

import com.gitter.socialapi.model.PublicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<PublicationEntity, Long> {

    List<PublicationEntity> findAll();
}