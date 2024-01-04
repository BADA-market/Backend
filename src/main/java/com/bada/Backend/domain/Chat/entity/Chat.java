package com.bada.Backend.domain.Chat.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
public class Chat {
    // 메시지  타입 : 입장, 채팅
    public enum MessageType{
        ENTER, TALK
    }
    @Id
    @GeneratedValue
    @Column(name = "chat_id")
    private Long id;
    private MessageType type; // 메시지 타입
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom; // 방 번호
    private String sender; // 발송자
    private String message; // 메시지
    private String time; // 채팅 발송 시간, 정렬 시 필요

}
