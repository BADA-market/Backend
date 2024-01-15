package com.bada.Backend.domain.trades.controller;

import com.bada.Backend.domain.trades.dto.TradeSearchDTO;
import com.bada.Backend.domain.trades.service.TradesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TradesController {
    private final TradesService tradesService;

    @PostMapping("/trades/{itemId}/{buyerId}")   // 상품, 구매자로 거래 내역 생성
    public String getLikes(@PathVariable("itemId") Long itemId, @PathVariable("buyerId") Long buyerId) {
        return tradesService.saveTrades(itemId, buyerId);
    }

    @PostMapping("/trades/cancel/{tradeId}")     // 거래 내역 취소
    public String deleteTrades(@PathVariable("tradeId") Long tradeId) {
        return tradesService.cancelTrades(tradeId);
    }

    @GetMapping("/trades/buy/{buyerId}")        // 구매 내역 조회
    public List<TradeSearchDTO> getBuyTrades(@PathVariable("buyerId") Long buyerId) { return tradesService.getBuyTrades(buyerId); }

    @GetMapping("/trades/sell/{sellerId}")      // 판매 내역 조회
    public List<TradeSearchDTO> getSellTrades(@PathVariable("sellerId") Long sellerId) { return tradesService.getSellTrades(sellerId); }
}
