package com.bada.Backend.domain.likes.entity;

import com.bada.Backend.domain.Item.entity.Item;
import com.bada.Backend.domain.User.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(schema = "likes")
@NoArgsConstructor
public class Likes {
    @Id
    @GeneratedValue
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @Builder
    public Likes(User user, Item item) {
        this.user = user;
        this.item = item;
    }

}
