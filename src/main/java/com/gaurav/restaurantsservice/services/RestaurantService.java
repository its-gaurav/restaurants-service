package com.gaurav.restaurantsservice.services;

import com.gaurav.restaurantsservice.domains.Item;
import com.gaurav.restaurantsservice.domains.ItemServed;
import com.gaurav.restaurantsservice.domains.Restaurant;
import com.gaurav.restaurantsservice.enums.FoodCategory;

import java.util.List;
import java.util.Set;

public interface RestaurantService {

    Long saveRestaurant(Restaurant restaurant);

    Restaurant getRestaurantById(Long restaurantId, boolean fetchAllDetails);

    List<Restaurant> getAllRestaurants(boolean fetchAllDetails);

    Long saveItemServed(ItemServed itemServed, Long restaurantId);

    void changeItemServedAvailability(Long itemMappingId, boolean availability);

    List<ItemServed> getAllItemsServedInRestaurant(Long restaurantId);

    ItemServed getAnItemServedInRestaurant(Long restaurantId, Long itemMappingId);

    void saveFoodCategories(Long restaurantId, Set<FoodCategory> foodCategories);

    Long saveItemToMaster(Item item, Long restaurantId);
}
