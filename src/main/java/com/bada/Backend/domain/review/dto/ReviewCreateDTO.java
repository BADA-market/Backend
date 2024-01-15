package com.bada.Backend.domain.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReviewCreateDTO {
    private String description;         // 리뷰
    private int waterTemperature;       // 바다 온도
}
