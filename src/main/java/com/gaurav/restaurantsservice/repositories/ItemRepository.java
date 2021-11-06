package com.gaurav.restaurantsservice.repositories;

import com.gaurav.restaurantsservice.entities.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    List<ItemEntity> findByIdIn(List<Long> idList);
}
