package com.gaurav.restaurantsservice.mappers;

import com.gaurav.restaurantsservice.domains.Item;
import com.gaurav.restaurantsservice.domains.ItemServed;
import com.gaurav.restaurantsservice.domains.Restaurant;
import com.gaurav.restaurantsservice.entities.ItemEntity;
import com.gaurav.restaurantsservice.entities.RestaurantEntity;
import com.gaurav.restaurantsservice.entities.RestaurantFoodCategoryMappingEntity;
import com.gaurav.restaurantsservice.entities.RestaurantItemMappingEntity;
import com.gaurav.restaurantsservice.enums.FoodCategory;
import com.gaurav.restaurantsservice.exceptions.ItemNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static com.gaurav.restaurantsservice.ExceptionConstants.*;

public class CustomRestaurantMapper {

    public static Restaurant map(RestaurantEntity restaurantEntity, List<RestaurantItemMappingEntity> itemsMapped, List<RestaurantFoodCategoryMappingEntity> foodCategoriesMapped, List<ItemEntity> itemEntities) {
        Restaurant restaurant = new Restaurant(restaurantEntity.getId(), restaurantEntity.getName(),
                restaurantEntity.getAddress(), restaurantEntity.getOpensAt(), restaurantEntity.getClosesAt(),
                restaurantEntity.getDiningCategory());
        restaurant.setClosed(restaurantEntity.isClosed());

        List<FoodCategory> categoriesServed = new ArrayList<>();
        foodCategoriesMapped.forEach(category-> categoriesServed.add(category.getFoodCategory()));
        restaurant.setFoodCategories(categoriesServed);

        List<ItemServed> itemsServed = new ArrayList<>();
        itemsMapped.forEach(itemMapped-> {
            ItemEntity itemEntity = itemEntities.stream().filter(item -> item.getId().equals(itemMapped.getItemId())).
                    findAny().orElseThrow(()-> new ItemNotFoundException(ITEM_NOT_FOUND));
            ItemServed item = new ItemServed(itemMapped.getId(),itemEntity.getName(),itemEntity.getFoodCategory(),
                    itemMapped.getPrice(), itemMapped.getRestaurantId(), itemEntity.getId(), itemMapped.getRatings(),
                    itemMapped.getAvgPreparationTime(), itemMapped.getServeType(), itemMapped.getServeQuantity(), itemMapped.isAvailable());

            itemsServed.add(item);
        });
        restaurant.setItemsServed(itemsServed);

        return restaurant;
    }

    public static Restaurant map(RestaurantEntity restaurantEntity, List<RestaurantFoodCategoryMappingEntity> filteredFoodCategories) {
        Restaurant restaurant = new Restaurant(restaurantEntity.getId(), restaurantEntity.getName(),
                restaurantEntity.getAddress(), restaurantEntity.getOpensAt(), restaurantEntity.getClosesAt(),
                restaurantEntity.getDiningCategory());
        List<FoodCategory> foodCategories = new ArrayList<>();
        filteredFoodCategories.forEach(category-> {
            foodCategories.add(category.getFoodCategory());
        });
        restaurant.setFoodCategories(foodCategories);
        return restaurant;
    }

    public static RestaurantEntity map(Restaurant restaurant) {
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setName(restaurant.getName());
        restaurantEntity.setAddress(restaurant.getAddress());
        restaurantEntity.setOpensAt(restaurant.getOpensAt());
        restaurantEntity.setClosesAt(restaurant.getClosesAt());
        restaurantEntity.setDiningCategory(restaurant.getDiningCategory());

        return restaurantEntity;
    }

    public static Item map(ItemEntity itemEntity) {
        return new Item(itemEntity.getId(),itemEntity.getName(),itemEntity.getFoodCategory());
    }

    public static RestaurantItemMappingEntity map(ItemServed itemServed) {
        RestaurantItemMappingEntity restaurantItemMappingEntity = new RestaurantItemMappingEntity();
        restaurantItemMappingEntity.setRestaurantId(itemServed.getRestaurantId());
        restaurantItemMappingEntity.setItemId(itemServed.getItemId());
        restaurantItemMappingEntity.setAvgPreparationTime(itemServed.getAvgPreparationTime());
        restaurantItemMappingEntity.setRatings(itemServed.getRatings());
        restaurantItemMappingEntity.setServeQuantity(itemServed.getServeQuantity());
        restaurantItemMappingEntity.setServeType(itemServed.getServeType());
        restaurantItemMappingEntity.setPrice(itemServed.getPrice());

        return restaurantItemMappingEntity;
    }

    public static ItemServed map(RestaurantItemMappingEntity itemMapped, ItemEntity itemEntity) {

        return new ItemServed(itemMapped.getId(),itemEntity.getName(),
                itemEntity.getFoodCategory(),itemMapped.getPrice(),itemMapped.getRestaurantId(),
                itemMapped.getItemId(),itemMapped.getRatings(),itemMapped.getAvgPreparationTime(),
                itemMapped.getServeType(),itemMapped.getServeQuantity(), itemMapped.isAvailable());
    }

    public static ItemEntity map(Item item) {
        return new ItemEntity(null, item.getName(), item.getFoodCategory());
    }
}
