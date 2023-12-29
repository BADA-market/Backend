package com.bada.Backend.domain.Chat.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Listener {

    @RabbitListener(queues = "chat.queue")
    public void processMessage(String content) {
        System.out.println(content);
    }
}
