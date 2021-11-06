package com.gaurav.restaurantsservice.services;

import com.gaurav.restaurantsservice.domains.Item;
import com.gaurav.restaurantsservice.entities.ItemEntity;
import com.gaurav.restaurantsservice.exceptions.ItemNotFoundException;
import com.gaurav.restaurantsservice.mappers.CustomRestaurantMapper;
import com.gaurav.restaurantsservice.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import static com.gaurav.restaurantsservice.ExceptionConstants.*;

@Component
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Override
    public Item getItemById(Long id) {
        ItemEntity itemEntity = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(ITEM_NOT_FOUND));
        return CustomRestaurantMapper.map(itemEntity);
    }
}
