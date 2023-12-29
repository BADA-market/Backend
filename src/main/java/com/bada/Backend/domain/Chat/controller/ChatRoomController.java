package com.bada.Backend.domain.Chat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        model.addAttribute("chatRoomId", chatRoomId);
        model.addAttribute("nickname", nickname);
        return "chat/room";
    }
}
