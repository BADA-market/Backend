package com.bada.Backend.domain.Item.service;

import com.bada.Backend.domain.Item.dto.ItemCreateDTO;
import com.bada.Backend.domain.Item.dto.ItemSearchDTO;
import com.bada.Backend.domain.Item.entity.Item;
import com.bada.Backend.domain.Item.repository.ItemRepository;
import com.bada.Backend.domain.User.entity.User;
import com.bada.Backend.domain.User.repository.UserRepository;
import com.bada.Backend.domain.likes.entity.Likes;
import com.bada.Backend.domain.likes.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final LikesRepository likesRepository;



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
    public List<ItemSearchDTO> getPopularItem(Long userId){
        List<Object[]> result = itemRepository.findItemsWithLikeCount();
        AtomicBoolean heart = new AtomicBoolean(false);
        return result.stream().filter(row->{
                Item item = (Item)row[0];
                Long likeCount = (Long) row[1];
                log.info("row[0]",item);
                log.info("row[1]",likeCount);
                log.info("row[2]",123445);
                System.out.println("123");
                System.out.println(likeCount);
                System.out.println(item.getTitle());
                System.out.println(item.getIs_deleted());
                return item != null && !item.getIs_deleted(); //이게 참이여야 다음으로 이동
            })
                .map(row -> { //item -> itemSearchDTO로 변환
                    Item item = (Item) row[0];
                    System.out.println("heart 진짜 나오냐?" + item.getTitle());
                    Optional<Likes> likesOptional = Optional.ofNullable(likesRepository.findByUserIdAndItemId(userId, item.getId()));
                    heart.set(likesOptional.isPresent());
                    ItemSearchDTO dto = ItemSearchDTO.from(item);
                    dto.setHeart(heart.get());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<ItemSearchDTO> getItem(Long userId){

        List<Item> items = itemRepository.findByOrderByCreatedAtDesc();
        AtomicBoolean heart = new AtomicBoolean(false);
        return items.stream()
                .filter(item->
                        item.getIs_deleted().equals(false) //Is_deleted == True 이면 조회x
                )
                .peek(item -> { //유저가 좋아요를 누른 게시물이라면 heart = true, 아니라면 heart = false로 설정
                    Optional<Likes> likesOptional = Optional.ofNullable(likesRepository.findByUserIdAndItemId(userId, item.getId()));
                    heart.set(likesOptional.isPresent());
                        })
                .map(ItemSearchDTO::from) //조회한 items리스트의 각 item을 ItemSearchDTO로 바꿈
                .peek(itemSearchDTO -> {
                        itemSearchDTO.setHeart(heart.get());
                })
                .collect(Collectors.toList()); //바뀐 ItemSearchDTO를 하나씩 배열에 집어넣음

    }

    public List<ItemSearchDTO> getItemByCategory(String category, Long userId){
        List<Item> byCategory = itemRepository.findByCategoryOrderByCreatedAtDesc(category);
        AtomicBoolean heart = new AtomicBoolean(false);
        return byCategory.stream()
                .filter(item->
                     item.getIs_deleted().equals(false)
                )
                .peek(item -> { //유저가 좋아요를 누른 게시물이라면 heart = true, 아니라면 heart = false로 설정
                    Optional<Likes> likesOptional = Optional.ofNullable(likesRepository.findByUserIdAndItemId(userId, item.getId()));
                    heart.set(likesOptional.isPresent());
                })
                .map(ItemSearchDTO::from)
                .peek(itemSearchDTO -> {
                    itemSearchDTO.setHeart(heart.get());
                })
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


    public List<ItemSearchDTO> getMyLikes(Long userId) {
        //일반 조회에서 filter 한줄 추가해서 구현
        List<Item> items = itemRepository.findByOrderByCreatedAtDesc();
        AtomicBoolean heart = new AtomicBoolean(false);
        return items.stream()
                .filter(item->
                        item.getIs_deleted().equals(false) //Is_deleted == True 이면 조회x
                )
                .peek(item -> { //유저가 좋아요를 누른 게시물이라면 heart = true, 아니라면 heart = false로 설정
                    Optional<Likes> likesOptional = Optional.ofNullable(likesRepository.findByUserIdAndItemId(userId, item.getId()));
                    heart.set(likesOptional.isPresent());
                })
                .filter(item -> heart.get() == true) //좋아요를 누른 게시물만 조회
                .map(ItemSearchDTO::from) //조회한 items리스트의 각 item을 ItemSearchDTO로 바꿈
                .peek(itemSearchDTO -> {
                    itemSearchDTO.setHeart(heart.get());
                })
                .collect(Collectors.toList()); //바뀐 ItemSearchDTO를 하나씩 배열에 집어넣음

    }
}
