package com.bada.Backend.domain.Item.entity;

import com.bada.Backend.domain.User.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue
    @Column(name = "Item_id")
    private Long id;

    private String title;

    private String description;

    private String picture_url;

    private int price;

    private int like_count; //이거 연관관계?

    private int view_count;

    private Boolean purchase_done;

    private Boolean is_deleted;

    @ManyToOne(fetch = FetchType.LAZY) //이거 JPA N+1문제 해결을 위한거던가?
    @JoinColumn(name = "user_id") // 이러면 user가 외래키로 테이블에 등록 반면 반대쪽인 User테이블에는 Item관련 속성이 없음
    private User user;



}
