package com.bada.Backend.domain.Chat.entity;

import com.bada.Backend.domain.Chat.Service.ChatService;
import com.bada.Backend.domain.User.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Entity
@Getter
@RequiredArgsConstructor
public class ChatRoom {
    @Id
    @Column(name = "chatroom_id")
    private String routingKey;
    private String roomName; // 채팅방 아이디, 물건 이름으로 하면 좋을 듯?
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller; // 여기서 User는 하나의 채팅방에 대한 판매자입니다.

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer; // 여기서 User는 하나의 채팅방에 대한 구매자입니다.

    @OneToMany(mappedBy = "chatRoom")
    private List<Chat> chats = new ArrayList<>();


    @Builder
    public ChatRoom(User seller, User buyer, String routingKey) {
        this.routingKey = routingKey;
        this.buyer = buyer;
        this.seller = seller;
    }



}
