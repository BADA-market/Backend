package com.bada.Backend.domain.Item.entity;

import com.bada.Backend.domain.BaseEntity;
import com.bada.Backend.domain.Item.dto.ItemSearchDTO;
import com.bada.Backend.domain.User.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor

public class Item extends BaseEntity {
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

    private String hope_location;

    private String category;


    @ManyToOne(fetch = FetchType.LAZY) //이거 JPA N+1문제 해결을 위한거던가?
    @JoinColumn(name = "user_id") // 이러면 user가 외래키로 테이블에 등록 반면 반대쪽인 User테이블에는 Item관련 속성이 없음
    private User user;

    @Builder //카테고리 추가 점
    public Item(String picture_url, String title, int price, String description, String hope_location, User user, String category){
        this.picture_url = picture_url;
        this.title = title;
        this.price = price;
        this.description = description;
        this.hope_location = hope_location;
        this.user = user;
        this.category = category;
        this.purchase_done = false; //안 팔림
        this.is_deleted = false; //안 삭제됨

    }

//    public Item UpdateItem(ItemSearchDTO itemSearchDTO) {
//        this.picture_url = itemSearchDTO.getPicture_url();
//        this.title = itemSearchDTO.getTitle();
//        this.price = itemSearchDTO.getPrice();
//        this.description = itemSearchDTO.getDescription();
//        this.category = itemSearchDTO.getCategory();
//        this.hope_location = itemSearchDTO.getHopeLocation();
//
//    }
//
    public void DeleteItem(){
        this.is_deleted = true;
    }

}
