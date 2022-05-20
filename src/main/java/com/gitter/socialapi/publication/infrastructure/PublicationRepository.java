package com.gitter.socialapi.publication.infrastructure;

import com.gitter.socialapi.publication.domain.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {}