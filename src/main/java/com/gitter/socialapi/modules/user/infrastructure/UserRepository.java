package com.gitter.socialapi.modules.user.infrastructure;

import com.gitter.socialapi.modules.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}