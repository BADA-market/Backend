package com.bada.Backend.domain.likes.controller;

import com.bada.Backend.domain.Item.service.ItemService;
import com.bada.Backend.domain.likes.service.LikesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikesController {
    private final LikesService likesService;

    @Operation(summary = "좋아요 생성", description = "좋아요 생성 여부를 반환합니다.")
    @Parameter(name = "userId", description = "유저 아이디")
    @Parameter(name = "itemId", description = "상품 아이디")
    @PostMapping("/likes/{userId}/{itemId}") //유저 정보와 아이템 정보로 좋아요를 만드는 기능
    public String getLikes(@PathVariable("userId") Long userId,@PathVariable("itemId") Long itemId){
        return likesService.getLikes(userId, itemId);
    }

    @Operation(summary = "좋아요 취소", description = "좋아요 취소 여부를 반환합니다.")
    @Parameter(name = "userId", description = "유저 아이디")
    @Parameter(name = "itemId", description = "상품 아이디")
    @DeleteMapping("/likes/{userId}/{itemId}")
    public String deleteLikes(@PathVariable("userId") Long userId,@PathVariable("itemId") Long itemId){
        return likesService.deleteLikes(userId, itemId);
    }
}
