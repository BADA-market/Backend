package com.bada.Backend.domain.Item.repository;

import com.bada.Backend.domain.Item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long> {

}
