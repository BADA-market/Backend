package com.bada.Backend.domain.Chat.controller;

import com.bada.Backend.domain.Chat.Service.ChatService;
import com.bada.Backend.domain.Chat.dto.ChatDto;
import com.bada.Backend.domain.Chat.dto.ChatRoomDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@Slf4j
public class StompRabbitController {

    private final ChatService chatService;

    private final RabbitTemplate template;

    private final static String CHAT_EXCHANGE_NAME = "chat.exchange";
    private final static String CHAT_QUEUE_NAME = "chat.queue";

    //이게 결국에는 채팅하기 버튼 클릭시 실행되는 로직에 되는거야
    @MessageMapping("chat.enter")
    public void enter(String routingKey, ChatDto chatDto) {
        chatDto.setMessage("입장하셨습니다.");
        chatDto.setRegDate(LocalDateTime.now());

        log.info("routingKey = {}", routingKey);
        //createRoom의 반환값을 라우팅키로 바꿔서 밑의 전송 로직의 목적지가 되어야 해


        // exchange
        template.convertAndSend(CHAT_EXCHANGE_NAME, "room." + routingKey, chatDto);
        // template.convertAndSend("room." + chatRoomId, chat); //queue
        // template.convertAndSend("amq.topic", "room." + chatRoomId, chat); //topic
    }

    //WebSocket을 통해 들어오는 메시지를 처리하는데 사용됩니다.
    @MessageMapping("chat.message.{chatRoomId}")
    public void send(ChatDto chatDto, @DestinationVariable String chatRoomId) {
        chatDto.setRegDate(LocalDateTime.now());

        template.convertAndSend(CHAT_EXCHANGE_NAME, "room." + chatRoomId, chatDto);
        //template.convertAndSend( "room." + chatRoomId, chat);
        //template.convertAndSend("amq.topic", "room." + chatRoomId, chat);
    }

    // receiver()는 단순히 큐에 들어온 메세지를 소비만 한다. (현재는 디버그 용도)
    // 지정한 큐에서 메세지를 비동기적으로 수신할 리스너를 정의한다.
    @RabbitListener(queues = CHAT_QUEUE_NAME)
    public void receive(ChatDto chatDto) {
        log.info("chatDto.getMessage() = {}",chatDto.getMessage());
    }
}