package com.bada.Backend.domain.Item.dto;



import com.bada.Backend.domain.Item.entity.Item;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter @Setter
public class ItemSearchDTO {
    private String picture_url;
    private String title;
    private int price;
    private String description;
    private String category;
    private String hopeLocation;
    private Boolean is_deleted;
    private int like_count;
    private Boolean purchase_done;
    private LocalDateTime createAt;

    private Long user_id;

    private Boolean heart; //좋아요 여부

    private Long itemId;

    //item객체 ItemSearchDTO로 변환하기
    //조회시(카테고리, 시간) 동작함
    public static ItemSearchDTO from(Item item) {
        ItemSearchDTO itemSearchDTO = new ItemSearchDTO();
        itemSearchDTO.setPicture_url(item.getPicture_url());
        itemSearchDTO.setTitle(item.getTitle());
        itemSearchDTO.setPrice(item.getPrice());
        itemSearchDTO.setDescription(item.getDescription());
        itemSearchDTO.setCategory(item.getCategory());
        itemSearchDTO.setHopeLocation(item.getHope_location());
        itemSearchDTO.setIs_deleted(item.getIs_deleted());
        itemSearchDTO.setLike_count(item.getLike_count());
        itemSearchDTO.setPurchase_done(item.getPurchase_done());
        itemSearchDTO.setCreateAt(item.getCreatedAt());
        itemSearchDTO.setUser_id(item.getUser().getId());
        itemSearchDTO.setItemId(item.getId());


        return itemSearchDTO;
    }

}
