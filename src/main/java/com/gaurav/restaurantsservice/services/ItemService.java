package com.gaurav.restaurantsservice.services;

import com.gaurav.restaurantsservice.domains.Item;

public interface ItemService {
    Item getItemById(Long id);
}
