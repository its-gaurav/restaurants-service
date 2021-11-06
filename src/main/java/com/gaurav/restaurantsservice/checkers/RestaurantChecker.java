package com.gaurav.restaurantsservice.checkers;

import com.gaurav.restaurantsservice.entities.RestaurantFoodCategoryMappingEntity;
import com.gaurav.restaurantsservice.entities.RestaurantItemMappingEntity;
import com.gaurav.restaurantsservice.enums.FoodCategory;
import com.gaurav.restaurantsservice.exceptions.*;
import com.gaurav.restaurantsservice.repositories.RestaurantFoodCategoryMappingRepository;
import com.gaurav.restaurantsservice.repositories.RestaurantItemMappingRepository;
import com.gaurav.restaurantsservice.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.gaurav.restaurantsservice.ExceptionConstants.*;

@Component
public class RestaurantChecker {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantItemMappingRepository restaurantItemMappingRepository;

    @Autowired
    private RestaurantFoodCategoryMappingRepository restaurantFoodCategoryMappingRepository;

    public void checkValidItemMappingId(Long restaurantId, Long itemMappingId) {

        restaurantRepository.findById(restaurantId).orElseThrow(()-> new RestaurantNotFoundException(RESTAURANT_NOT_FOUND));

        RestaurantItemMappingEntity itemMapped = restaurantItemMappingRepository.findById(itemMappingId).
                orElseThrow(() -> new ItemMappingNotFoundException(ITEM_SERVED_NOT_FOUND));

        if(!itemMapped.getRestaurantId().equals(restaurantId)){
            throw new InvalidItemMappingIdException(ITEM_SERVED_ID_INVALID);
        }
    }

    public void checkFoodCategoryAlreadyExist(Long restaurantId, Set<FoodCategory> foodCategories) {
        List<RestaurantFoodCategoryMappingEntity> categoriesAlreadyMapped = restaurantFoodCategoryMappingRepository.
                findByRestaurantId(restaurantId);
        List<ServiceExceptions> serviceExceptions = new ArrayList<>();
        foodCategories.forEach(category-> {
            if(categoriesAlreadyMapped.stream().map(RestaurantFoodCategoryMappingEntity::getFoodCategory).
                    collect(Collectors.toList()).contains(category)){
                serviceExceptions.add(new ServiceExceptions(category.getDescription()+" already exists"));
            }
        });
        if(!CollectionUtils.isEmpty(serviceExceptions)){
            throw new CategoryAlreadyExistException(serviceExceptions.stream().
                    map(ServiceExceptions::getMessage).collect(Collectors.toList()));
        }
    }
}
