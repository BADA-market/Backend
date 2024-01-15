package com.bada.Backend.domain.review.controller;

import com.bada.Backend.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    // 리뷰 생성
    @PostMapping("/review/{tradeId}/{description}/{waterTemperature}")
    public String createReview(@PathVariable("tradeId") Long tradeId,
                               @PathVariable("description") String description,
                               @PathVariable("waterTemperature") int waterTemperature) {

        return reviewService.createReview(tradeId, description, waterTemperature);
    }

    // 리뷰 삭제
    @PostMapping("/review/delete/{reviewId}")
    public String deleteReview(@PathVariable("reviewId") Long reviewId) {

        return reviewService.deleteReview(reviewId);
    }
}
