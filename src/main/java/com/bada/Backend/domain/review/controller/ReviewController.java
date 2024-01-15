package com.bada.Backend.domain.review.controller;

import com.bada.Backend.domain.review.dto.ReviewCreateDTO;
import com.bada.Backend.domain.review.dto.ReviewSearchDTO;
import com.bada.Backend.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    // 리뷰 생성
    @PostMapping("/review/create/{tradeId}")
    public String createReview(@PathVariable("tradeId") Long tradeId, @RequestBody ReviewCreateDTO reviewCreateDTO) throws Exception {

        return reviewService.createReview(tradeId, reviewCreateDTO);
    }

    // 리뷰 삭제
    @PostMapping("/review/delete/{reviewId}")
    public String deleteReview(@PathVariable("reviewId") Long reviewId) {

        return reviewService.deleteReview(reviewId);
    }

    // 작성한 리뷰 조회
    @GetMapping("/review/search/{buyerId}")
    public List<ReviewSearchDTO> getReview(@PathVariable("buyerId") Long buyerId) {
        return reviewService.getReview(buyerId);
    }
}
