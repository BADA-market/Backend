package com.bada.Backend.domain.review.controller;

import com.bada.Backend.domain.review.dto.ReviewCreateDTO;
import com.bada.Backend.domain.review.dto.ReviewSearchDTO;
import com.bada.Backend.domain.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @Operation(summary = "리뷰 생성", description = "리뷰 생성 여부를 반환합니다.")
    @Parameter(name = "tradeId", description = "거래 아이디")
    @PostMapping("/review/create/{tradeId}")        // 리뷰 생성
    public String createReview(@PathVariable("tradeId") Long tradeId, @RequestBody ReviewCreateDTO reviewCreateDTO) throws Exception {

        return reviewService.createReview(tradeId, reviewCreateDTO);
    }

    @Operation(summary = "리뷰 삭제", description = "리뷰 삭제 여부를 반환합니다.")
    @Parameter(name = "reviewId", description = "리뷰 아이디")
    @PostMapping("/review/delete/{reviewId}")       // 리뷰 삭제
    public String deleteReview(@PathVariable("reviewId") Long reviewId) {

        return reviewService.deleteReview(reviewId);
    }

    @Operation(summary = "작성한 리뷰 조회", description = "작성한 리뷰들을 반환합니다.")
    @Parameter(name = "buyerId", description = "구매자 아이디")
    @GetMapping("/review/search/post/{buyerId}")     // 작성한 리뷰 조회
    public List<ReviewSearchDTO> getReview(@PathVariable("buyerId") Long buyerId) {

        return reviewService.getPostReview(buyerId);
    }

    @Operation(summary = "받은 리뷰 조회", description = "받은 리뷰들을 반환합니다.")
    @Parameter(name = "sellerId", description = "판매자 아이디")
    @GetMapping("/review/search/send/{sellerId}")     // 받은 리뷰 조회
    public List<ReviewSearchDTO> getSendReview(@PathVariable("sellerId") Long sellerId) {

        return reviewService.getSendReview(sellerId);
    }

    @Operation(summary = "리뷰 수정", description = "리뷰 수정 여부를 반환합니다.")
    @Parameter(name = "reviewId", description = "리뷰 아이디")
    @PostMapping("/review/update/{reviewId}")         // 리뷰 수정
    public String updateReview(@PathVariable("reviewId") Long reviewId, @RequestBody ReviewCreateDTO reviewUpdateDTO) {

        return reviewService.updateReview(reviewId, reviewUpdateDTO);
    }
}
