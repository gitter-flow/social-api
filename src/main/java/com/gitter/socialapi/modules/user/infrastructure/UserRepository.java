package com.gitter.socialapi.modules.user.infrastructure;

import com.gitter.socialapi.modules.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query(
            value = "select u from users u where u.username like :username order by u.createdAt desc"
    )
    List<User> selectWhereUsernameLike(@Param("username") String username, Pageable pageable);
    User findByUsername(String username);
    User findByEmail(String email);
}       