package com.bada.Backend.domain.Chat.Service;

import com.bada.Backend.domain.Chat.dto.ChatRoomDto;
import com.bada.Backend.domain.Chat.entity.ChatRoom;
import com.bada.Backend.domain.Chat.repository.ChatRoomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@Slf4j
@Data
@Service
@RequiredArgsConstructor
public class ChatService {
    private final ObjectMapper mapper;


    private final ChatRoomRepository chatRoomRepository;


    //사용자가 지금까지 만든 채팅방 목록을 보여주기 위한 메소드
    //상세설계 필요
    public List<ChatRoom> findRoomByBuyerId(Long buyerId){
        //return chatRooms.get(roomId);
        return chatRoomRepository.findByBuyer(buyerId);
    }
    @Transactional
    public ChatRoom createRoom(ChatRoomDto chatRoomDto) {

        // Builder 를 이용해서 ChatRoom 을 Building
        ChatRoom room = ChatRoom.builder()
                .routingKey(UUID.randomUUID().toString())
                .seller(chatRoomDto.getSeller())
                .buyer(chatRoomDto.getBuyer())
                .build();

        //chatRoom을 만들었으니까 seller와 buyer에게 chatroom 정보 추가 -> 조회


        //이게 엔티티에 추가하는 느낌인가보네
        chatRoomRepository.save(room); //변환
        return room;
    }

}
