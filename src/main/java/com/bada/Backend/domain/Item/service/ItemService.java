package com.bada.Backend.domain.Item.service;

import com.bada.Backend.domain.Item.dto.ItemCreateDTO;
import com.bada.Backend.domain.Item.dto.ItemSearchDTO;
import com.bada.Backend.domain.Item.entity.Item;
import com.bada.Backend.domain.Item.repository.ItemRepository;
import com.bada.Backend.domain.User.entity.User;
import com.bada.Backend.domain.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;



    @Transactional
    public String createItem(ItemCreateDTO itemCreateDTO, Long user_id){
        User findUser = userRepository.findById(user_id).get();
        Item newItem = Item.builder()
                .picture_url(itemCreateDTO.getPicture_url())
                .title(itemCreateDTO.getTitle())
                .price(itemCreateDTO.getPrice())
                .description(itemCreateDTO.getDescription())
                .hope_location(itemCreateDTO.getHopeLocation())
                .user(findUser)
                .category(itemCreateDTO.getCategory()).build();
        itemRepository.save(newItem);
        return "itemCreated";

    }

    public List<ItemSearchDTO> getItem(){

        List<Item> items = itemRepository.findByOrderByCreatedAtDesc();
        return items.stream()
                .filter(item->
                        item.getIs_deleted().equals(false)
                )
                .map(ItemSearchDTO::from)
                .collect(Collectors.toList());

    }

    public List<ItemSearchDTO> getItemByCategory(String category){
        List<Item> byCategory = itemRepository.findByCategoryOrderByCreatedAtDesc(category);
        return byCategory.stream()
                .filter(item->
                     item.getIs_deleted().equals(false)
                )
                .map(ItemSearchDTO::from)
                .collect(Collectors.toList());

    }
    @Transactional
    public String deleteItem(Long itemId){
        //삭제할 때 is_deleted를 true로 바꿈 -> 아 조회할 때 is_deleted false만 조회해야 하네..
        //이런것들 확인하려고 테스트코드 짜나봐~
        Item findItem = itemRepository.findById(itemId).get();
        String itemTitle = findItem.getTitle();
        findItem.DeleteItem();

        return itemTitle +" is deleted";
    }

    @Transactional
    public String updateItem(Long itemId,ItemSearchDTO itemSearchDTO){
        Item findItem = itemRepository.findById(itemId).get();
        return findItem.UpdateItem(itemSearchDTO);

    }

}
