package com.bada.Backend.domain.trades.controller;

import com.bada.Backend.domain.trades.dto.TradeSearchDTO;
import com.bada.Backend.domain.trades.service.TradesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Operation(summary = "거래 내역 생성", description = "거래 내역 생성 여부를 반환합니다.")
    @Parameter(name = "itemId", description = "상품의 아이디")
    @Parameter(name = "buyerId", description = "구매자의 아이디")
    @PostMapping("/trades/{itemId}/{buyerId}")   // 상품, 구매자로 거래 내역 생성
    public String getLikes(@PathVariable("itemId") Long itemId, @PathVariable("buyerId") Long buyerId) {
        return tradesService.saveTrades(itemId, buyerId);
    }

    @Operation(summary = "거래 내역 취소", description = "거래 내역 취소 여부를 반환합니다.")
    @Parameter(name = "tradeId", description = "거래 아이디")
    @PostMapping("/trades/cancel/{tradeId}")     // 거래 내역 취소
    public String cancelTrades(@PathVariable("tradeId") Long tradeId) {
        return tradesService.cancelTrades(tradeId);
    }

    @Operation(summary = "구매 내역 조회", description = "구매 내역을 반환합니다.")
    @Parameter(name = "buyerId", description = "구매자 아이디")
    @GetMapping("/trades/buy/{buyerId}")        // 구매 내역 조회
    public List<TradeSearchDTO> getBuyTrades(@PathVariable("buyerId") Long buyerId) { return tradesService.getBuyTrades(buyerId); }

    @Operation(summary = "판매 내역 조회", description = "판매 내역을 반환합니다.")
    @Parameter(name = "sellerId", description = "판매자 아이디")
    @GetMapping("/trades/sell/{sellerId}")      // 판매 내역 조회
    public List<TradeSearchDTO> getSellTrades(@PathVariable("sellerId") Long sellerId) { return tradesService.getSellTrades(sellerId); }

    @Operation(summary = "거래 내역 삭제", description = "거래 내역 삭제 여부를 반환합니다.")
    @Parameter(name = "tradeId", description = "거래 아이디")
    @PostMapping("/trades/delete/{tradeId}")    // 거래 내역 삭제
    public String deleteTrades(@PathVariable("tradeId") Long tradeId) { return tradesService.deleteTrades(tradeId); }
}
