package com.bada.Backend.domain.review.dto;

import com.bada.Backend.domain.Item.entity.Item;
import com.bada.Backend.domain.review.entity.Review;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewSearchDTO {

    private Long id;
    private Long seller_id;
    private String title;
    private int price;
    private String description;
    private int waterTemperature;

    public static ReviewSearchDTO from(Review review) {
        Item item = review.getTrade().getItem();
        ReviewSearchDTO reviewSearchDTO = new ReviewSearchDTO();
        reviewSearchDTO.setId(review.getId());
        reviewSearchDTO.setSeller_id(item.getUser().getId());
        reviewSearchDTO.setTitle(item.getTitle());
        reviewSearchDTO.setPrice(item.getPrice());
        reviewSearchDTO.setDescription(review.getDescription());
        reviewSearchDTO.setWaterTemperature(review.getWaterTemperature());

        return reviewSearchDTO;
    }
}
