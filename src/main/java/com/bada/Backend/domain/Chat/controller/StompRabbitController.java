package com.bada.Backend.domain.Chat.controller;

import com.bada.Backend.domain.Chat.Service.ChatService;
import com.bada.Backend.domain.Chat.dto.ChatDto;
import com.bada.Backend.domain.Chat.dto.ChatRoomDto;
import com.bada.Backend.domain.Chat.entity.Chat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class StompRabbitController {

    private final ChatService chatService;

    private final RabbitTemplate template;

    private final static String CHAT_EXCHANGE_NAME = "chat.exchange";
    private final static String CHAT_QUEUE_NAME = "chat.queue";



    //이게 결국에는 채팅하기 버튼 클릭시 실행되는 로직에 되는거야
    @MessageMapping("chat.enter.{routingKey}")
    public void enter(@DestinationVariable String routingKey, ChatDto chatDto) {

        List<Chat> chats = chatService.chatHistory(routingKey);
        if (!chats.isEmpty())
        {

        }

    }

    //WebSocket을 통해 들어오는 메시지를 처리하는데 사용됩니다.
    @MessageMapping("chat.message.{routingKey}")
    public void send(ChatDto chatDto, @DestinationVariable String routingKey) {
        chatDto.setRegDate(LocalDateTime.now());

        template.convertAndSend(CHAT_EXCHANGE_NAME, "room." + routingKey, chatDto);
        //template.convertAndSend( "room." + chatRoomId, chat);
        //template.convertAndSend("amq.topic", "room." + chatRoomId, chat);

        //여기서는 chat 엔티티를 만들어서 DB에 저장하는 로직을 넣어야 해
        chatService.chatSave(chatDto, routingKey);

    }

    // receiver()는 단순히 큐에 들어온 메세지를 소비만 한다. (현재는 디버그 용도)
    // 지정한 큐에서 메세지를 비동기적으로 수신할 리스너를 정의한다.
    @RabbitListener(queues = CHAT_QUEUE_NAME)
    public void receive(ChatDto chatDto) {
        log.info("chatDto.getMessage() = {}",chatDto.getMessage());
    }
}