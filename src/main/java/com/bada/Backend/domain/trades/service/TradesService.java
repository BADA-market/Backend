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
    public String saveTrades(Long ItemId, Long buyerId) {
        Item findItem = itemRepository.findById(ItemId).get();
        User buyer = userRepository.findById(buyerId).get();

        // 아이템 판매완료 설정 추가
        findItem.setPurchase_done(true);
        itemRepository.save(findItem);

        Trades trade = Trades.builder().buyer(buyer)
                .item(findItem)
                .build();

        tradesRepository.save(trade);

        return "Trades Success";
    }

    @Transactional      // 거래 취소
    public String cancelTrades(Long tradeId) {
        Trades findTrades = tradesRepository.findById(tradeId).get();
        findTrades.CancelPurchase();

        // 아이템 판매중 설정으로 변경
        Item setItem = findTrades.getItem();
        setItem.setPurchase_done(false);
        itemRepository.save(setItem);

        tradesRepository.save(findTrades);

        return "Trades Cancel";
    }

    @Transactional      // 구매 내역 조회
    public List<TradeSearchDTO> getBuyTrades(Long buyerId) {
        List<Trades> list = tradesRepository.findAll();

        return list.stream()
                .filter(trades -> trades.getBuyer().getId() == buyerId)
                .filter(trades -> trades.isPurchase_done())
                .map(TradeSearchDTO::from)
                .collect(Collectors.toList());
    }

    @Transactional      // 판매 내역 조회
    public List<TradeSearchDTO> getSellTrades(Long sellerId) {
        List<Trades> list = tradesRepository.findAll();

        return list.stream()
                .filter(trades -> trades.getItem().getUser().getId() == sellerId)
                .filter(trades -> trades.isPurchase_done())
                .map(TradeSearchDTO::from)
                .collect(Collectors.toList());
    }
}
