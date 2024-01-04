package com.bada.Backend.domain.Chat.Service;

import com.bada.Backend.domain.Chat.dto.ChatRoomDto;
import com.bada.Backend.domain.Chat.entity.ChatRoom;
import com.bada.Backend.domain.Chat.repository.ChatRoomRepository;
import com.bada.Backend.domain.User.entity.User;
import com.bada.Backend.domain.User.repository.UserRepository;
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
    private final UserRepository userRepository;


    //사용자가 지금까지 만든 채팅방 목록을 보여주기 위한 메소드
    //상세설계 필요
    public List<ChatRoom> findRoomByBuyerId(Long buyerId){
        //return chatRooms.get(roomId);
        return chatRoomRepository.findByBuyerId(buyerId);
    }
    @Transactional
    public String createRoom(ChatRoomDto chatRoomDto) {
        //여기서 매개변수 더 받아서 이미 존재하는 룸이면 안만들게끔하는 로직 추가해야 해!!
        List<ChatRoom> chatroom = chatRoomRepository.findByBuyerIdAndSellerId(chatRoomDto.getBuyer(), chatRoomDto.getSeller());
        if (chatroom.isEmpty()) {

            User Seller = userRepository.findById(chatRoomDto.getSeller()).get();
            User Buyer = userRepository.findById(chatRoomDto.getBuyer()).get();

            // Builder 를 이용해서 ChatRoom 을 Building
            ChatRoom room = ChatRoom.builder()
                    .routingKey(UUID.randomUUID().toString())
                    .seller(Seller)
                    .buyer(Buyer)
                    .build();

            Seller.getChatRooms().add(room);
            Buyer.getChatRooms().add(room);

            //chatRoom을 만들었으니까 seller와 buyer에게 chatroom 정보 추가 -> 조회


            //이게 엔티티에 추가하는 느낌인가보네
            chatRoomRepository.save(room); //변환
            return room.getRoutingKey();
        }
        else{
            return chatroom.get(0).getRoutingKey();
        }
    }


}
