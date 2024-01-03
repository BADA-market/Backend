package com.bada.Backend.domain.Chat.repository;

import com.bada.Backend.domain.Chat.entity.ChatRoom;
import com.bada.Backend.domain.Item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,String> {
    List<ChatRoom> findByBuyer(Long buyer);
}
