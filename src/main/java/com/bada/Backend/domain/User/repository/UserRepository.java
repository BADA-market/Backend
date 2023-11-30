package com.bada.Backend.domain.User.repository;

import com.bada.Backend.domain.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);

    Optional<User> findByLoginIdAndPassword(String loginId, String password);


}
