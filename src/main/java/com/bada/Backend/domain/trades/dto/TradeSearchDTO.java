package com.bada.Backend.domain.trades.dto;

import com.bada.Backend.domain.Item.entity.Item;
import com.bada.Backend.domain.trades.entity.Trades;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradeSearchDTO {

    private Long id;
    private Long seller_id;
    private Long buyer_id;
    private String title;
    private int price;
    private String category;

    public static TradeSearchDTO from(Trades trade) {
        TradeSearchDTO tradeSearchDTO = new TradeSearchDTO();
        tradeSearchDTO.setId(trade.getId());
        tradeSearchDTO.setSeller_id(trade.getItem().getUser().getId());
        tradeSearchDTO.setBuyer_id(trade.getBuyer().getId());
        tradeSearchDTO.setTitle(trade.getItem().getTitle());
        tradeSearchDTO.setPrice(trade.getItem().getPrice());
        tradeSearchDTO.setCategory(trade.getItem().getCategory());

        return tradeSearchDTO;
    }
}
