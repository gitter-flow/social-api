package com.gitter.socialapi.modules.team.infrastructure;

import com.gitter.socialapi.modules.team.domain.Team;
import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, String> {
    void deleteById(@Param("id") @NonNull String id);
    
}