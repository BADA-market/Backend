package com.bada.Backend.domain.Chat.Service;

import com.bada.Backend.domain.Chat.dto.ChatDto;
import com.bada.Backend.domain.Chat.dto.ChatRoomDto;
import com.bada.Backend.domain.Chat.entity.Chat;
import com.bada.Backend.domain.Chat.entity.ChatRoom;
import com.bada.Backend.domain.Chat.repository.ChatRepository;
import com.bada.Backend.domain.Chat.repository.ChatRoomRepository;

import com.bada.Backend.domain.User.entity.User;
import com.bada.Backend.domain.User.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Data
@Service
@RequiredArgsConstructor
public class ChatService {
    private final ObjectMapper mapper;


    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;


    //사용자가 구매자인 채팅방 목록 조회
    public List<ChatRoomDto> findRoomByBuyerId(Long buyerId){
        List<ChatRoom> byBuyerId = chatRoomRepository.findByBuyerId(buyerId);
        return byBuyerId.stream().map(chatRoom -> {
            ChatRoomDto chatroomdto = ChatRoomDto.builder().seller(chatRoom.getSeller().getId())
                    .buyer(chatRoom.getBuyer().getId())
                    //item 관련 코드는 전부 빼놈
                    .routingKey(chatRoom.getRoutingKey()).build();
            return chatroomdto;
        }).collect(Collectors.toList());
    }

    //사용자가 판매자인 채팅방 목록 조회
    public List<ChatRoomDto> findRoomBySellerId(Long sellerId){
        List<ChatRoom> bySellerId = chatRoomRepository.findBySellerId(sellerId);
        return bySellerId.stream().map(chatRoom -> {
            ChatRoomDto chatroomdto = ChatRoomDto.builder().seller(chatRoom.getSeller().getId())
                    .buyer(chatRoom.getBuyer().getId())
                    .routingKey(chatRoom.getRoutingKey()).build();
            return chatroomdto;
        }).collect(Collectors.toList());
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

            //chatRoom을 만들었으니까 seller와 buyer에게 chatroom 정보 추가 -> 조회
            Seller.getChatRooms().add(room); //아니 DB에 추가되는 것도 아닌데 이거 왜 추가하는거야 맨날??
            Buyer.getChatRooms().add(room);




            chatRoomRepository.save(room); //변환
            return room.getRoutingKey();
        }
        else{
            return chatroom.get(0).getRoutingKey();
        }
    }

    @Transactional
    @Async
    public void chatSave(ChatDto chatDto, String routingKey){

        ChatRoom chatRoom = chatRoomRepository.findById(routingKey).get();


        Chat chat = Chat.builder()
                .sender(chatDto.getSender())
                .nickname(chatDto.getNickname())
                .time(chatDto.getRegDate().toString())
                .message(chatDto.getMessage())
                .chatRoom(chatRoom).build();

        chatRepository.save(chat);
    }

    @Transactional
    @Async
    public List<Chat> chatHistory(String routingKey){
        List<Chat> chatList = chatRepository.findByChatRoomRoutingKey(routingKey);

        return chatList;

    }

    @Transactional
    public Long deleteRoom(String routingKey){
        return chatRoomRepository.deleteByRoutingKey(routingKey);
    }




}
