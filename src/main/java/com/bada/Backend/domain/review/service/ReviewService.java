package com.bada.Backend.domain.review.service;

import com.bada.Backend.domain.User.repository.UserRepository;
import com.bada.Backend.domain.review.entity.Review;
import com.bada.Backend.domain.review.repository.ReviewRepository;
import com.bada.Backend.domain.trades.entity.Trades;
import com.bada.Backend.domain.trades.repository.TradesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final TradesRepository tradesRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Transactional      // 리뷰 작성
    public String createReview(Long tradeId, String description, int waterTemperature) {
        Trades trade = tradesRepository.getById(tradeId);

        Review newReview = Review.builder().trade(trade)
                            .build();

        newReview.setDetail(description, waterTemperature);

        reviewRepository.save(newReview);

        return "Review is Created";
    }

    @Transactional      // 리뷰 삭제
    public String deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);

        return "Review is Deleted";
    }
}
