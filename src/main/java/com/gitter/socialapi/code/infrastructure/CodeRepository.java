package com.gitter.socialapi.code.infrastructure;

import com.gitter.socialapi.code.domain.CodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeRepository extends JpaRepository<CodeEntity, Long> {

    List<CodeEntity> findAll();
}