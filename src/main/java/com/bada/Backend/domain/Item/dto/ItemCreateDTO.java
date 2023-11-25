package com.bada.Backend.domain.Item.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ItemCreateDTO {
    private String picture_url;
    private String title;
    private int price;
    private String description;
    private String category;
    private String hopeLocation;



}
