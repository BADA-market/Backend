package com.bada.Backend.domain.Chat.controller;

import com.bada.Backend.domain.Chat.Service.ChatService;
import com.bada.Backend.domain.Chat.dto.ChatRoomDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/chat")
@Slf4j
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatService chatService;

    @GetMapping("/rooms")
    public String getRooms() {

        System.out.println("1234");
        return "chat/rooms";
    }

    @GetMapping("/room")
    public String createRoom(String nickname, Model model, String seller, String buyer) {
        long sellerId = Long.parseLong(seller);
        long buyerId = Long.parseLong(buyer);

        ChatRoomDto chatRoomDto = new ChatRoomDto(sellerId, buyerId);
        String routingKey = chatService.createRoom(chatRoomDto); //채팅방 생성 -> 라우팅 키 획득

        model.addAttribute("routingKey", routingKey);
        model.addAttribute("nickname", nickname);
        model.addAttribute("seller", sellerId);
        model.addAttribute("buyer", buyerId);
        log.info("nickname = {}", nickname);
        log.info("seller = {}", seller);
        log.info("buyer = {}", buyer);
        return "chat/room";
    }
}
