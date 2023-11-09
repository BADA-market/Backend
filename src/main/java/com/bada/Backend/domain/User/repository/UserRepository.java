package com.bada.Backend.domain.User.repository;

import com.bada.Backend.domain.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
