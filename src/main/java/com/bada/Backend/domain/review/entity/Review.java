package com.bada.Backend.domain.review.entity;

import com.bada.Backend.domain.trades.entity.Trades;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(schema = "reviews")
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="trade_id")
    private Trades trade;

    private String description;

    private int waterTemperature;

    @Builder
    public Review(Trades trade) {
        this.trade = trade;
    }

    public void setDetail(String description, int waterTemperature) {
        this.description = description;
        this.waterTemperature = waterTemperature;
    }
}
