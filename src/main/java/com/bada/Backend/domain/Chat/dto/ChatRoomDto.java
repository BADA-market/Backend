package com.bada.Backend.domain.Chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class ChatRoomDto {
    //private String routingKey;
    private Long seller;
    private Long buyer;

    public ChatRoomDto(Long seller, Long buyer) {
        this.seller = seller;
        this.buyer = buyer;
    }

}