package com.bada.Backend.domain.trade.controller;

import com.bada.Backend.domain.trade.service.TradesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TradesController {
    private final TradesService tradesService;

    @PostMapping("/trades/{sellerId}/{buyerId}/{itemId}")   // 판매자, 구매자, 상품으로 거래 내역 생성
    public String getLikes(@PathVariable("sellerId") Long sellerId, @PathVariable("buyerId") Long buyerId, @PathVariable("itemId") Long itemId) {
        return tradesService.saveTrades(sellerId, buyerId, itemId);
    }

    @PostMapping("/trades/{tradeId}")     // 거래 내역 취소
    public String deleteTrades(@PathVariable("tradeId") Long tradeId) {
        return tradesService.cancelTrades(tradeId);
    }
}
