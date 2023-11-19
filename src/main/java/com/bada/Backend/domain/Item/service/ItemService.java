package com.bada.Backend.domain.Item.service;

import com.bada.Backend.domain.Item.dto.ItemCreateDTO;
import com.bada.Backend.domain.Item.entity.Item;
import com.bada.Backend.domain.Item.repository.ItemRepository;
import com.bada.Backend.domain.User.entity.User;
import com.bada.Backend.domain.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Transactional
    public String createItem(ItemCreateDTO itemCreateDTO, Long user_id){
        User findUser = userRepository.findById(user_id).get();
        Item newItem = Item.builder().picture_url(itemCreateDTO.getPicture_url())
                .title(itemCreateDTO.getTitle())
                .price(itemCreateDTO.getPrice())
                .description(itemCreateDTO.getDescription())
                .hope_location(itemCreateDTO.getDescription())
                .user(findUser)
                .category(itemCreateDTO.getCategory()).build();
        itemRepository.save(newItem);
        return "itemCreated";

    }

}
