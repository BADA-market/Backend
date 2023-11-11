package com.bada.Backend.domain.likes.repository;

import com.bada.Backend.domain.likes.entity.like;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LikesRepository extends JpaRepository<like,Long> {
}
