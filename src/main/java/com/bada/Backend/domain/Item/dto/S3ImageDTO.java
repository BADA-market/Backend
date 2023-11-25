package com.bada.Backend.domain.Item.dto;

import lombok.Getter;

@Getter
public class S3ImageDTO {
    private String image_url;

    public S3ImageDTO(String image_url) {
        this.image_url = image_url;
    }
}