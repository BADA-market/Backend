package com.bada.Backend.domain.Chat.entity;

import com.bada.Backend.domain.Chat.Service.ChatService;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Entity
@RequiredArgsConstructor
public class ChatRoom {
    @Id
    @Column(name = "chatroom_id")
    private String routingKey;
    private String roomName; // 채팅방 아이디, 물건 이름으로 하면 좋을 듯?
    private Long seller; // 판매자, 1:1 채팅방이니까 2명으로 구현
    private Long buyer; // 구매자

    @OneToMany(mappedBy = "chatRoom")
    private List<Chat> chats = new ArrayList<>();


    @Builder
    public ChatRoom(Long seller, Long buyer, String routingKey) {
        this.routingKey = routingKey;
        this.buyer = buyer;
        this.seller = seller;

    }



}
