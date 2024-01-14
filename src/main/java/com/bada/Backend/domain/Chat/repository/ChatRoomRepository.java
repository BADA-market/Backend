package com.bada.Backend.domain.Chat.repository;

import com.bada.Backend.domain.Chat.entity.ChatRoom;
import com.bada.Backend.domain.Item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,String> {
    List<ChatRoom> findByBuyerId(Long buyerId); // 자기가 구매자인 경우 채팅방 목록 조회
    List<ChatRoom> findBySellerId(Long buyerId); // 자기가 판매자인 경우 채팅방 목록 조회

    List<ChatRoom> findByBuyerIdAndSellerId(Long buyerId, Long sellerId);

    Long deleteByRoutingKey(String routingKey); //반환값은 삭제된 엔티티의 개수

}
