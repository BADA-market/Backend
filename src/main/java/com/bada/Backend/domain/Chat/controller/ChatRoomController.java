package com.bada.Backend.domain.Chat.controller;

import com.bada.Backend.domain.Chat.Service.ChatService;
import com.bada.Backend.domain.Chat.dto.ChatRoomDto;
import com.bada.Backend.domain.Chat.entity.ChatRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

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
    // 나중에 받는 데이터를 JSON으로 수정할 필요가 있어
    // 지금은 html을 내가 만들어서 form-data로 데이터를 받느라 이 형태야
    @GetMapping("/room") //url에 직접 입력해서 확인중이라 GetMapping으로 함
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

    @DeleteMapping("/room/{routingKey}")
    @ResponseBody
    public Long deleteRoom(@PathVariable String routingKey) {
        return chatService.deleteRoom(routingKey);
    }

    @PostMapping("/room/{userId}")
    @ResponseBody
    public Map<String,List<ChatRoomDto>> getRoomList(@PathVariable Long userId) {
        Map<String, List<ChatRoomDto>> roomMap = new ConcurrentHashMap<>();
        roomMap.put("buyer", chatService.findRoomByBuyerId(userId));
        roomMap.put("seller", chatService.findRoomBySellerId(userId));

        return roomMap;
    }
}
