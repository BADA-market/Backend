package com.bada.Backend.domain.review.service;

import com.bada.Backend.domain.User.repository.UserRepository;
import com.bada.Backend.domain.review.dto.ReviewCreateDTO;
import com.bada.Backend.domain.review.dto.ReviewSearchDTO;
import com.bada.Backend.domain.review.entity.Review;
import com.bada.Backend.domain.review.repository.ReviewRepository;
import com.bada.Backend.domain.trades.dto.TradeSearchDTO;
import com.bada.Backend.domain.trades.entity.Trades;
import com.bada.Backend.domain.trades.repository.TradesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final TradesRepository tradesRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Transactional      // 리뷰 작성
    public String createReview(Long tradeId, ReviewCreateDTO reviewCreateDTO) {
        Trades trade = tradesRepository.getById(tradeId);

        Review newReview = Review.builder().trade(trade)
                            .build();

        newReview.setDetail(reviewCreateDTO.getDescription(), reviewCreateDTO.getWaterTemperature());

        reviewRepository.save(newReview);

        return "Review is created";
    }

    @Transactional      // 리뷰 삭제
    public String deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);

        return "Review is deleted";
    }

    @Transactional      // 리뷰 조회
    public List<ReviewSearchDTO> getReview(Long buyerId) {
        List<Review> list = reviewRepository.findAll();

        return list.stream()
                .filter(review -> review.getTrade().getBuyer().getId() == buyerId)
                .map(ReviewSearchDTO::from)
                .collect(Collectors.toList());
    }
}
