package com.bada.Backend.domain.Item.repository;

import com.bada.Backend.domain.Item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {
    List<Item> findByOrderByCreatedAtDesc(); //ASC

    List<Item> findByCategoryOrderByCreatedAtDesc(String category);
    //Item 엔티티와 좋아요의 개수를 담은 리스트가 반환됩니다.
    //이 리스트는 Object[] 타입의 배열로 각 배열의 첫 번째 요소에는 Item 엔티티,
    // 두 번째 요소에는 좋아요의 개수가 들어갑니다.
//    @Query("SELECT i, COUNT(l) " +
//            "FROM Item i LEFT JOIN i.likesList l " +
//            "GROUP BY i " +
//            "ORDER BY COUNT(l) DESC, i.createdAt DESC")
//    List<Object[]> findItemsWithLikeCount(@Param("limit") int limit);
}
