package com.bada.Backend.domain.Item.repository;

import com.bada.Backend.domain.Item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {
    List<Item> findByOrderByCreatedAtDesc(); //ASC

    List<Item> findByCategoryOrderByCreatedAtDesc(String category);

}
