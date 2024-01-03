package com.bada.Backend.domain.Chat.repository;

import com.bada.Backend.domain.Chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat,Long> {
}
