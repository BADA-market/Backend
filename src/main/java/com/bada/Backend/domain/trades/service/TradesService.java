package com.bada.Backend.domain.trades.service;

import com.bada.Backend.domain.Item.entity.Item;
import com.bada.Backend.domain.Item.repository.ItemRepository;
import com.bada.Backend.domain.User.entity.User;
import com.bada.Backend.domain.User.repository.UserRepository;
import com.bada.Backend.domain.trades.dto.TradeSearchDTO;
import com.bada.Backend.domain.trades.entity.Trades;
import com.bada.Backend.domain.trades.repository.TradesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TradesService {
    private final TradesRepository tradesRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Transactional      // 거래 내역 생성(성립)
    public String saveTrades(Long ItemId, Long buyerId){
        Item findItem = itemRepository.findById(ItemId).get();
        User buyer = userRepository.findById(buyerId).get();
        Trades trade = Trades.builder().buyer(buyer)
                .item(findItem)
                .build();
        tradesRepository.save(trade);
        return "Trades Success";
    }

    @Transactional      // 거래 취소
    public String cancelTrades(Long tradeId){
        Trades findTrades = tradesRepository.findById(tradeId).get();
        findTrades.CancelPurchase();
        tradesRepository.save(findTrades);
        return "Trades Cancel";
    }

    @Transactional      // 거래 내역 조회
    public List<TradeSearchDTO> getTrades(Long buyerId) {
        List<Trades> list = tradesRepository.findAll();
        return list.stream()
                .filter(trades -> trades.getBuyer().getId() == buyerId)
                .filter(trades -> trades.isPurchase_done())
                .map(TradeSearchDTO::from)
                .collect(Collectors.toList());
    }
}
