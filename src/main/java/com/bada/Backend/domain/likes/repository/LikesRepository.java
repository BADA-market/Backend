package com.bada.Backend.domain.likes.repository;

import com.bada.Backend.domain.likes.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LikesRepository extends JpaRepository<Likes,Long> {
    Likes findByUserIdAndItemId(Long UserId,Long ItemId);
}
