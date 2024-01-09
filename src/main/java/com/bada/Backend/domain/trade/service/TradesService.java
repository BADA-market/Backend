package com.bada.Backend.domain.trade.service;

import com.bada.Backend.domain.Item.entity.Item;
import com.bada.Backend.domain.Item.repository.ItemRepository;
import com.bada.Backend.domain.User.entity.User;
import com.bada.Backend.domain.User.repository.UserRepository;
import com.bada.Backend.domain.likes.entity.Likes;
import com.bada.Backend.domain.trade.entity.Trades;
import com.bada.Backend.domain.trade.repository.TradesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TradesService {
    private final TradesRepository tradesRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Transactional // 거래 내역 생성(성립)
    public String saveTrades(Long sellerId, Long buyerId, Long ItemId){
        User seller = userRepository.findById(sellerId).get();
        User buyer = userRepository.findById(buyerId).get();
        Item findItem = itemRepository.findById(ItemId).get();
        Trades trade = Trades.builder().seller(seller)
                .buyer(buyer)
                .item(findItem)
                .build();
        tradesRepository.save(trade);
        return "Trades created";
    }

    @Transactional // 거래 취소
    public String cancelTrades(Long tradeId){
        Trades findTrades = tradesRepository.findById(tradeId).get();
        findTrades.CancelPurchase();
        tradesRepository.save(findTrades);
        return "Trades Cancel";
    }


}
