package com.gaurav.restaurantsservice.services;

import com.gaurav.restaurantsservice.domains.Restaurant;

import java.util.List;

public interface RestaurantsService {

    public Long saveRestaurant(Restaurant restaurant);

    public Restaurant getRestaurantById(Long restaurantId);

    public List<Restaurant> getAllRestaurants();
}
