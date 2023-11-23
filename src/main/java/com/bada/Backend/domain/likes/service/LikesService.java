package com.bada.Backend.domain.likes.service;

import com.bada.Backend.domain.Item.entity.Item;
import com.bada.Backend.domain.Item.repository.ItemRepository;
import com.bada.Backend.domain.User.entity.User;
import com.bada.Backend.domain.User.repository.UserRepository;
import com.bada.Backend.domain.likes.entity.Likes;
import com.bada.Backend.domain.likes.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final LikesRepository likesRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    @Transactional // 좋아요 만들기
    public String getLikes(Long userId, Long ItemId){
        User findUser = userRepository.findById(userId).get();
        Item findItem = itemRepository.findById(ItemId).get();
        Likes like = Likes.builder().user(findUser)
                .item(findItem)
                .build();
        likesRepository.save(like);
        return "likes created";
    }

    @Transactional // 좋아요 취소
    public String deleteLikes(Long userId, Long ItemId){
        Likes findLikes = likesRepository.findByUserIdAndItemId(userId, ItemId);
        likesRepository.delete(findLikes);
        return "likes deleted";
    }


}
