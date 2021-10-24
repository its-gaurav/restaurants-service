package com.gaurav.restaurantsservice.controllers;

import com.gaurav.restaurantsservice.domains.Item;
import com.gaurav.restaurantsservice.domains.Restaurant;
import com.gaurav.restaurantsservice.services.RestaurantsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class RestaurantRestController {

    @Autowired
    RestaurantsService restaurantsService;

    @GetMapping(path = "/{restaurantId}")
    public Restaurant getRestaurant(@PathVariable("restaurantId") Long restaurantId){
        return restaurantsService.getRestaurantById(restaurantId);
    }

    @GetMapping()
    public List<Restaurant> getAllRestaurants(){
        return restaurantsService.getAllRestaurants();
    }

    @PostMapping()
    public ResponseEntity<Object> addRestaurant(@RequestBody Restaurant restaurant){
        log.info("-------- Saving Restaurant {} ----------",restaurant);
        Long savedRestaurantId = restaurantsService.saveRestaurant(restaurant);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedRestaurantId).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/{restaurantId}/item")
    public ResponseEntity<Object> addItemToRestaurant(@PathVariable("restaurantId") Long restaurantId, @RequestBody Item item){
        log.info("-------- Saving Restaurant {} ----------",restaurant);
        Long savedRestaurantId = restaurantsService.saveRestaurant(restaurant);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedRestaurantId).toUri();
        return ResponseEntity.created(location).build();
    }
}
