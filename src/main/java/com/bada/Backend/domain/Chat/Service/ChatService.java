package com.bada.Backend.domain.Chat.Service;

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
    private Map<String, ChatRoom> chatRooms;

    private final ChatRoomRepository chatRoomRepository;

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    public List<ChatRoom> findAllRoom(){ //이거는 언제 쓰는거지? 채팅룸 조회인데 필요x
        return new ArrayList<>(chatRooms.values());
    }

    public ChatRoom findRoomById(String roomId){
        //return chatRooms.get(roomId);
        return chatRoomRepository.findById(roomId).get();
    }
    @Transactional
    public ChatRoom createRoom(String name) {
        String roomId = UUID.randomUUID().toString(); // 랜덤한 방 아이디 생성

        // Builder 를 이용해서 ChatRoom 을 Building
        ChatRoom room = ChatRoom.builder()
                .roomId(roomId)
                .name(name) //이름은 그냥 상대방 이름으로 하자
                .build();

        //chatRooms.put(roomId, room); // 랜덤 아이디와 room 정보를 Map 에 저장
        //이게 엔티티에 추가하는 느낌인가보네
        chatRoomRepository.save(room); //변환
        return room;
    }
    //WeSocketSession은 어떻게 쓰는걸까?
    public <T> void sendMessage(WebSocketSession session, T message) {
        try{ //TextMessage는 payload 관련
            session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
