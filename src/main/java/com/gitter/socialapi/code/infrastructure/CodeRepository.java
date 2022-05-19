package com.gitter.socialapi.code;

import com.gitter.socialapi.code.CodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodeRepository extends JpaRepository<CodeEntity, Long> {

    List<CodeEntity> findAll();
}