package com.bada.Backend.domain.Item.controller;

import com.bada.Backend.domain.Item.dto.ItemCreateDTO;
import com.bada.Backend.domain.Item.dto.ItemSearchDTO;
import com.bada.Backend.domain.Item.dto.S3ImageDTO;
import com.bada.Backend.domain.Item.entity.Item;
import com.bada.Backend.domain.Item.service.ItemService;
import com.bada.Backend.domain.Item.service.S3UploadService;
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
    @PostMapping("/item/{user_id}")
    public String create(@RequestBody ItemCreateDTO itemCreateDTO, @PathVariable("user_id") Long user_id){
        return itemService.createItem(itemCreateDTO, user_id);
        //뭐 return 할까? 객체? ok?
    }

    @PostMapping("/image")
    public S3ImageDTO uploadReview(@RequestParam("multipartFile") MultipartFile multipartFile) throws IOException {
        S3ImageDTO s3ImageDto = new S3ImageDTO(s3UploadService.saveFile(multipartFile));
        return s3ImageDto;

    }

    @GetMapping("/item/time") //시간순 조회
    public List<ItemSearchDTO> getItem(){
        return itemService.getItem();
    }

    @GetMapping("/item/{category}")
    public List<ItemSearchDTO> getItemByCategory(@PathVariable("category") String category){
        return itemService.getItemByCategory(category);
    }

    @DeleteMapping("/item/{itemId}")
    public String deleteItem(@PathVariable("itemId") Long itemId){
        return itemService.deleteItem(itemId);
    }

}
