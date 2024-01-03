package com.bada.Backend.domain.Chat.controller;

import com.bada.Backend.domain.Chat.Service.ChatService;
import com.bada.Backend.domain.Chat.dto.ChatRoomDto;
import com.bada.Backend.domain.Chat.entity.ChatRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatController {

    private final ChatService service;
    //채팅하기 버튼을 클릭하면 실행되는 컨트롤러
    //라우팅 키는 uuid로 생성
    @PostMapping("/chat")
    public ChatRoom createRoom(@RequestBody ChatRoomDto chatRoomDto){
        return service.createRoom(chatRoomDto);
    }

    //채팅방 조회하는 기능
//    @GetMapping("/chat")
//    public List<ChatRoom> findAllRooms(){
//        return service.findAllRoom();
//    }
}
