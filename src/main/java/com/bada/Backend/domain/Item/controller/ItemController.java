package com.bada.Backend.domain.Item.controller;

import com.bada.Backend.domain.Item.dto.ItemCreateDTO;
import com.bada.Backend.domain.Item.dto.ItemSearchDTO;
import com.bada.Backend.domain.Item.dto.S3ImageDTO;
import com.bada.Backend.domain.Item.entity.Item;
import com.bada.Backend.domain.Item.service.ItemService;
import com.bada.Backend.domain.Item.service.S3UploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final S3UploadService s3UploadService;

    @Operation(summary = "상품 생성", description = "상품 생성 여부를 반환합니다.")
    @Parameter(name = "userId", description = "유저 아이디")
    @PostMapping("/item/{userId}")
    public String create(@RequestBody ItemCreateDTO itemCreateDTO, @PathVariable("userId") Long user_id){
        return itemService.createItem(itemCreateDTO, user_id);
        //뭐 return 할까? 객체? ok?
    }

    @Operation(summary = "이미지 업로드", description = "이미지 업로드 여부를 반환합니다.")
    @PostMapping("/image")
    public S3ImageDTO uploadReview(@RequestParam("multipartFile") MultipartFile multipartFile) throws IOException {
        S3ImageDTO s3ImageDto = new S3ImageDTO(s3UploadService.saveFile(multipartFile));
        return s3ImageDto;

    }

    @Operation(summary = "시간순 조회", description = "시간순으로 상품 조회 내역을 반환합니다.")
    @Parameter(name = "userId", description = "유저 아이디")
    @GetMapping("/item/time/{userId}") //시간순 조회
    public List<ItemSearchDTO> getItem(@PathVariable("userId") Long userId){
        return itemService.getItem(userId);
    }

    @Operation(summary = "카테고리별 조회", description = "카테고리별 상품 조회 내역을 반환합니다.")
    @Parameter(name = "userId", description = "유저 아이디")
    @Parameter(name = "category", description = "카테고리")
    @GetMapping("/item/{category}/{userId}")
    public List<ItemSearchDTO> getItemByCategory(@PathVariable("category") String category,@PathVariable("userId") Long userId){
        return itemService.getItemByCategory(category, userId);
    }

    @Operation(summary = "상품 제거", description = "상품 제거 여부를 반환합니다.")
    @Parameter(name = "itemId", description = "상품 아이디")
    @DeleteMapping("/item/{itemId}")
    public String deleteItem(@PathVariable("itemId") Long itemId){
        return itemService.deleteItem(itemId);
    }

    @Operation(summary = "상품 수정", description = "상품 수정 여부를 반환합니다.")
    @Parameter(name = "itemId", description = "상품 아이디")
    @PatchMapping("item/{itemId}")
    public String UpdateItem(@RequestBody ItemSearchDTO itemSearchDTO, @PathVariable("itemId") Long itemId)
    {
        return itemService.updateItem(itemId,itemSearchDTO);
    }

    @Operation(summary = "좋아요순 조회", description = "좋아요순으로 상품 조회 내역을 반환합니다.")
    @Parameter(name = "userId", description = "유저 아이디")
    @GetMapping("/item/likes/{userId}")
    public List<ItemSearchDTO> getPopularItem(@PathVariable("userId") Long userId){
        return itemService.getPopularItem(userId);
    }

}
