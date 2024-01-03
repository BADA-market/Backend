package com.bada.Backend.domain.Chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    @GetMapping("/rooms")
    public String getRooms() {

        System.out.println("1234");
        return "chat/rooms";
    }

    @GetMapping("/room")
    public String getRoom(Long chatRoomId, String nickname, Model model) {
        //채팅방 번호를 uuid로 만들고 -> 라우팅키로 사용
        model.addAttribute("chatRoomId", UUID.randomUUID());
        model.addAttribute("nickname", nickname);
        return "chat/room";
    }
}
