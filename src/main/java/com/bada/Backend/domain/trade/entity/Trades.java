package com.bada.Backend.domain.trade.entity;

import com.bada.Backend.domain.Item.entity.Item;
import com.bada.Backend.domain.User.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(schema = "trades")
@NoArgsConstructor
public class Trades {
    @Id
    @GeneratedValue
    @Column(name = "trade_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="seller_id")
    private User seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="buyer_id")
    private User buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    private boolean purchase_done;

    @Builder
    public Trades(User seller, User buyer, Item item) {
        this.seller = seller;
        this.buyer = buyer;
        this.item = item;
        this.purchase_done = true;
    }

    public void CancelPurchase() { this.purchase_done = false; }

}