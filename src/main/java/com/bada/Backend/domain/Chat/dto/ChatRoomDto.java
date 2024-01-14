package com.bada.Backend.domain.Chat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class ChatRoomDto {
    //private String routingKey;
    private Long seller;
    private Long buyer;
    private Long itemId;
    private String routingKey;

    public ChatRoomDto(Long seller, Long buyer) {
        this.seller = seller;
        this.buyer = buyer;
    }
    @Builder
    //채팅룸 조회를 위한 생성자
    public ChatRoomDto(Long seller, Long buyer, Long itemId, String routingKey) {
        this.seller = seller;
        this.buyer = buyer;
        this.itemId = itemId;
        this.routingKey = routingKey;
    }

}