package com.bada.Backend.domain.likes.controller;

import com.bada.Backend.domain.Item.service.ItemService;
import com.bada.Backend.domain.likes.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikesController {
    private final LikesService likesService;
    @PostMapping("/likes/{userId}/{itemId}") //유저 정보와 아이템 정보로 좋아요를 만드는 기ㅇㄷ
    public String getLikes(@PathVariable("userId") Long userId,@PathVariable("itemId") Long itemId){
        return likesService.getLikes(userId, itemId);
    }

    @DeleteMapping("/likes/{userId}/{itemId}")
    public String deleteLikes(@PathVariable("userId") Long userId,@PathVariable("itemId") Long itemId){
        return likesService.deleteLikes(userId, itemId);
    }
}
