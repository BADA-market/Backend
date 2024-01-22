package com.bada.Backend.domain.review.service;

import com.bada.Backend.domain.User.repository.UserRepository;
import com.bada.Backend.domain.User.entity.User;
import com.bada.Backend.domain.review.dto.ReviewCreateDTO;
import com.bada.Backend.domain.review.dto.ReviewSearchDTO;
import com.bada.Backend.domain.review.entity.Review;
import com.bada.Backend.domain.review.repository.ReviewRepository;
import com.bada.Backend.domain.trades.entity.Trades;
import com.bada.Backend.domain.trades.repository.TradesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        // 리뷰에 작성된 수온을 포함해줌
        User seller = trade.getItem().getUser();
        double value = seller.valueWaterTemperature(reviewCreateDTO.getWaterTemperature());
        seller.setWaterTemperature(value);

        userRepository.save(seller);

        newReview.setDetail(reviewCreateDTO.getDescription(), reviewCreateDTO.getWaterTemperature());

        reviewRepository.save(newReview);

        return "Review is created";
    }

    @Transactional      // 리뷰 삭제
    public String deleteReview(Long reviewId) {
        Review review = reviewRepository.getById(reviewId);

        review.deleteReview();

        reviewRepository.save(review);

        return "Review is deleted";
    }

    @Transactional      // 내가 쓴 리뷰 조회
    public List<ReviewSearchDTO> getPostReview(Long buyerId) {
        List<Review> list = reviewRepository.findAll();

        return list.stream()
                .filter(review -> review.getTrade().getBuyer().getId() == buyerId)
                .filter(review -> !review.is_deleted())
                .map(ReviewSearchDTO::from)
                .collect(Collectors.toList());
    }

    @Transactional      // 내가 받은 리뷰 조회
    public List<ReviewSearchDTO> getSendReview(Long sellerId) {
        List<Review> list = reviewRepository.findAll();

        return list.stream()
                .filter(review -> review.getTrade().getItem().getUser().getId() == sellerId)
                .filter(review -> !review.is_deleted())
                .map(ReviewSearchDTO::from)
                .collect(Collectors.toList());
    }

    @Transactional      // 리뷰 수정
    public String updateReview(Long reviewId, ReviewCreateDTO reviewUpdateDTO) {
        Review updateReview = reviewRepository.getById(reviewId);

        // 수정 전 리뷰의 수온이 포함된 값을 제거해줌
        User seller = updateReview.getTrade().getItem().getUser();
        double value = seller.valueWaterTemperature(updateReview.getWaterTemperature()) * -1;
        seller.setWaterTemperature(value);

        updateReview.setDetail(reviewUpdateDTO.getDescription(), reviewUpdateDTO.getWaterTemperature());

        // 수정 후 리뷰의 수온을 포함해줌
        value = seller.valueWaterTemperature(updateReview.getWaterTemperature());
        seller.setWaterTemperature(value);

        userRepository.save(seller);

        reviewRepository.save(updateReview);

        return "Review is updated";
    }
}
