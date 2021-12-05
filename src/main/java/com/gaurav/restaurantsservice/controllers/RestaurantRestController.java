package com.gaurav.restaurantsservice.controllers;

import com.gaurav.restaurantsservice.Configuration;
import com.gaurav.restaurantsservice.checkers.RestaurantChecker;
import com.gaurav.restaurantsservice.domains.*;
import com.gaurav.restaurantsservice.enums.FoodCategory;
import com.gaurav.restaurantsservice.services.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@Slf4j
public class RestaurantRestController {

    @Autowired
    RestaurantService restaurantsService;

    @Autowired
    Configuration configuration;

    @Autowired
    RestaurantChecker restaurantChecker;

    @PostMapping("/")
    public ResponseEntity<Object> addRestaurant(@RequestBody @Valid Restaurant restaurant){
        log.info("-------- Saving Restaurant {} ----------",restaurant);
        /*
        * A way to make call from 1 service to another
        *   HashMap<String,String> params = new HashMap<>();
            params.put("name", "input/pathVariable");
            ResponseEntity<Restaurant> response = new RestTemplate().getForEntity("localhost:8080/users/{name}", Restaurant.class, params);
        * */
        Long savedRestaurantId = restaurantsService.saveRestaurant(restaurant);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedRestaurantId).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(path = "/{restaurantId}")
    public Restaurant getRestaurant(@PathVariable("restaurantId") Long restaurantId){
        return restaurantsService.getRestaurantById(restaurantId, true);
    }

    @GetMapping()
    public List<Restaurant> getAllRestaurants(@RequestParam(value = "fetchAllDetails", defaultValue = "false", required = false) boolean fetchAllDetails){
        return restaurantsService.getAllRestaurants(fetchAllDetails);
    }

    @GetMapping("/config")
    public Limits getConfig(){
        return new Limits(configuration.getMinimum(),configuration.getMaximum());
    }



    @PostMapping("/{restaurantId}/items-served")
    public ResponseEntity<Object> addItemToRestaurant(@PathVariable("restaurantId") Long restaurantId, @RequestBody @Valid ItemServed itemServed){
        log.info("-------- Saving ItemServed {} ----------",itemServed);
        Long savedItemServedId = restaurantsService.saveItemServed(itemServed, restaurantId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedItemServedId).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{restaurantId}/items-served")
    public List<ItemServed> getAllItemsServedInRestaurant(@PathVariable("restaurantId") Long restaurantId){
        return restaurantsService.getAllItemsServedInRestaurant(restaurantId);
    }

    @PutMapping("/{restaurantId}/items-served/{itemMappingId}")
    public ResponseEntity<Object> changeItemServedAvailabilityInRestaurant(@PathVariable("restaurantId") Long restaurantId,
                                                                           @PathVariable("itemMappingId") Long itemMappingId,
                                                                     @RequestParam(name = "availability") boolean availability){
        log.info("-------- Changing Item-served {} availability to {} ----------",itemMappingId,availability);
        restaurantChecker.checkValidItemMappingId(restaurantId, itemMappingId);
        restaurantsService.changeItemServedAvailability(itemMappingId, availability);
        URI location = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest().toUriString());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{restaurantId}/items-served/{itemMappingId}")
    public ItemServed getAnItemServedInRestaurant(@PathVariable("restaurantId") Long restaurantId,
                                                                           @PathVariable("itemMappingId") Long itemMappingId){
        restaurantChecker.checkValidItemMappingId(restaurantId, itemMappingId);
        return restaurantsService.getAnItemServedInRestaurant(restaurantId, itemMappingId);
    }

    @PostMapping("/{restaurantId}/food-categories")
    public ResponseEntity<Object> addFoodCategoriesToRestaurant(@PathVariable("restaurantId") Long restaurantId, @RequestBody Set<FoodCategory> foodCategories){
        log.info("-------- Adding Food-Categories {} ----------",foodCategories);
        restaurantChecker.checkFoodCategoryAlreadyExist(restaurantId, foodCategories);
        restaurantsService.saveFoodCategories(restaurantId, foodCategories);
        URI location = URI.create(ServletUriComponentsBuilder
                .fromCurrentRequest().toUriString());
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/{restaurantId}/items")
    public ResponseEntity<Object> addItemToMaster(@PathVariable("restaurantId") Long restaurantId, @RequestBody @Valid Item item){
        log.info("-------- Saving Item {} ----------",item);
        Long savedItemServedId = restaurantsService.saveItemToMaster(item, restaurantId);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedItemServedId).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/{restaurantId}/checkout-details")
    public OrderDetails checkoutDetails(@RequestBody OrderDetails orderDetails, @PathVariable("restaurantId") Long restaurantId){
        log.info("----- Checking Out Details ------");
        restaurantChecker.checkValidOrderDetails(orderDetails, restaurantId);
        return restaurantsService.checkoutOrderDetails(orderDetails);
    }

    @PostMapping("/{restaurantId}/order")
    public OrderDetails orderFood(@RequestBody OrderDetails orderDetails, @PathVariable("restaurantId") Long restaurantId){
        log.info("----- Ordering Food ------");
        restaurantChecker.checkValidOrderDetails(orderDetails, restaurantId);
        orderDetails = restaurantsService.checkoutOrderDetails(orderDetails);
        restaurantsService.orderFood(orderDetails);

        return orderDetails;
    }
}
