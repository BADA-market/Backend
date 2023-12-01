package com.bada.Backend.domain.Chat.entity;

import com.bada.Backend.domain.Chat.Service.ChatService;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
@Entity
@RequiredArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue
    @Column(name = "chatroom_id")
    private Long id;
    private String roomId; // 채팅방 아이디
    private String name; // 채팅방 이름
    //private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(String roomId, String name){
        this.roomId = roomId;
        this.name = name;
    }

//    public void handleAction(WebSocketSession session, Chat message, ChatService service) {
//        // message 에 담긴 타입을 확인한다.
//        // 이때 message 에서 getType 으로 가져온 내용이
//        // Chat 의 열거형인 MessageType 안에 있는 ENTER 과 동일한 값이라면
//        if (message.getType().equals(Chat.MessageType.ENTER)) {
//            // sessions 에 넘어온 session 을 담고,
//            sessions.add(session);
//
//            // message 에는 입장하였다는 메시지를 띄운다
//            message.setMessage(message.getSender() + " 님이 입장하셨습니다");
//            sendMessage(message, service);
//        } else if (message.getType().equals(Chat.MessageType.TALK)) {
//            message.setMessage(message.getMessage()); //이거는 뭐하는 짓이지??
//            sendMessage(message, service); //service를 보내는건 또 첨보네, 롬복 쓰면 될듯?
//        }
//    }
//
//    public <T> void sendMessage(T message, ChatService service) {
//        sessions.parallelStream().forEach(session -> service.sendMessage(session, message));
//    }

}
