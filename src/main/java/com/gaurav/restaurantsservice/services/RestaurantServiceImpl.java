package com.gaurav.restaurantsservice.services;

import com.gaurav.restaurantsservice.domains.Item;
import com.gaurav.restaurantsservice.domains.ItemServed;
import com.gaurav.restaurantsservice.domains.Restaurant;
import com.gaurav.restaurantsservice.entities.ItemEntity;
import com.gaurav.restaurantsservice.entities.RestaurantEntity;
import com.gaurav.restaurantsservice.entities.RestaurantFoodCategoryMappingEntity;
import com.gaurav.restaurantsservice.entities.RestaurantItemMappingEntity;
import com.gaurav.restaurantsservice.enums.FoodCategory;
import com.gaurav.restaurantsservice.exceptions.ItemMappingNotFoundException;
import com.gaurav.restaurantsservice.exceptions.ItemNotFoundException;
import com.gaurav.restaurantsservice.exceptions.RestaurantNotFoundException;
import com.gaurav.restaurantsservice.mappers.CustomRestaurantMapper;
import com.gaurav.restaurantsservice.repositories.ItemRepository;
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
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantItemMappingRepository restaurantItemMappingRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private RestaurantFoodCategoryMappingRepository restaurantFoodCategoryMappingRepository;

    @Autowired
    private ItemService itemService;

    @Override
    public Long saveRestaurant(Restaurant restaurant) {
        RestaurantEntity restaurantEntity = CustomRestaurantMapper.map(restaurant);
        restaurantRepository.save(restaurantEntity);
        List<RestaurantFoodCategoryMappingEntity> categoryMappingEntities = new ArrayList<>();
        restaurant.getFoodCategories().forEach(foodCategory -> {
            RestaurantFoodCategoryMappingEntity restaurantFoodCategoryMapping =
                    new RestaurantFoodCategoryMappingEntity(null,restaurantEntity.getId(),foodCategory);
            categoryMappingEntities.add(restaurantFoodCategoryMapping);
        });
        restaurantFoodCategoryMappingRepository.saveAll(categoryMappingEntities);
        return restaurantEntity.getId();
    }

    @Override
    public Restaurant getRestaurantById(Long restaurantId, boolean fetchAllDetails) {
        RestaurantEntity restaurantEntity = restaurantRepository.findById(restaurantId).
                orElseThrow(() -> new RestaurantNotFoundException(RESTAURANT_NOT_FOUND));
        List<RestaurantFoodCategoryMappingEntity> foodCategoriesMapped = restaurantFoodCategoryMappingRepository.findByRestaurantId(restaurantId);

        Restaurant restaurant;
        if(fetchAllDetails){
            List<RestaurantItemMappingEntity> itemsMapped = restaurantItemMappingRepository.findByRestaurantId(restaurantId);
            List<ItemEntity> itemEntities = new ArrayList<>();
            if(!CollectionUtils.isEmpty(itemsMapped)){
                itemEntities = itemRepository.findByIdIn(itemsMapped.stream().map(RestaurantItemMappingEntity::getItemId).collect(Collectors.toList()));
            }
            restaurant = CustomRestaurantMapper.map(restaurantEntity, itemsMapped, foodCategoriesMapped, itemEntities);
        }else{
            restaurant = CustomRestaurantMapper.map(restaurantEntity, foodCategoriesMapped);
        }
        return restaurant;
    }

    @Override
    public List<Restaurant> getAllRestaurants(boolean fetchAllDetails) {
        List<RestaurantEntity> restaurantEntities = restaurantRepository.findAll();
        List<Restaurant> restaurants = new ArrayList<>();
        if(!CollectionUtils.isEmpty(restaurantEntities)){
            List<RestaurantFoodCategoryMappingEntity> foodCategoriesMapped = restaurantFoodCategoryMappingRepository.
                    findByRestaurantIdIn(restaurantEntities.stream().map(RestaurantEntity::getId).collect(Collectors.toList()));
            restaurantEntities.forEach(restaurantEntity -> {
                List<RestaurantFoodCategoryMappingEntity> filteredFoodCategories = foodCategoriesMapped.
                        stream().filter(category-> category.getRestaurantId().equals(restaurantEntity.getId())).collect(Collectors.toList());
                Restaurant restaurant = CustomRestaurantMapper.map(restaurantEntity, filteredFoodCategories);
                restaurants.add(restaurant);
                if(fetchAllDetails){
                    // add items-served details as well
                }
            });
        }
        return restaurants;
    }

    @Override
    public Long saveItemServed(ItemServed itemServed, Long restaurantId) {
        Restaurant restaurant = this.getRestaurantById(restaurantId, false);
        itemServed.setRestaurantId(restaurantId);
        Item item = itemService.getItemById(itemServed.getItemId());
        itemServed.setName(item.getName());
        itemServed.setFoodCategory(item.getFoodCategory());

        RestaurantItemMappingEntity itemServedEntity = CustomRestaurantMapper.map(itemServed);

        return restaurantItemMappingRepository.save(itemServedEntity).getId();
    }

    @Override
    public void changeItemServedAvailability(Long itemMappingId, boolean availability) {
        RestaurantItemMappingEntity itemMapped = restaurantItemMappingRepository.findById(itemMappingId).
                orElseThrow(() -> new ItemMappingNotFoundException(ITEM_SERVED_NOT_FOUND));
        itemMapped.setAvailable(availability);
        restaurantItemMappingRepository.save(itemMapped);
    }

    @Override
    public List<ItemServed> getAllItemsServedInRestaurant(Long restaurantId) {
        this.getRestaurantById(restaurantId,false);
        List<ItemServed> itemsServed = new ArrayList<>();
        List<RestaurantItemMappingEntity> itemsMapped = restaurantItemMappingRepository.findByRestaurantId(restaurantId);
        if(!CollectionUtils.isEmpty(itemsMapped)){
            List<ItemEntity> items = itemRepository.findByIdIn(itemsMapped.stream().
                    map(RestaurantItemMappingEntity::getItemId).collect(Collectors.toList()));
            itemsMapped.forEach(itemMapped-> {
                ItemEntity itemEntity = items.stream().filter(i-> i.getId().equals(itemMapped.getItemId())).
                        findAny().orElseThrow(()-> new ItemNotFoundException(ITEM_NOT_FOUND));
                itemsServed.add(CustomRestaurantMapper.map(itemMapped, itemEntity));
            });
        }

        return itemsServed;

    }

    @Override
    public ItemServed getAnItemServedInRestaurant(Long restaurantId, Long itemMappingId) {
        RestaurantItemMappingEntity itemMapping = restaurantItemMappingRepository.findById(itemMappingId).
                orElseThrow(() -> new ItemMappingNotFoundException(ITEM_SERVED_NOT_FOUND));
        ItemEntity itemEntity = itemRepository.findById(itemMapping.getItemId()).
                orElseThrow(() -> new ItemMappingNotFoundException(ITEM_NOT_FOUND));
        return CustomRestaurantMapper.map(itemMapping, itemEntity);
    }

    @Override
    public void saveFoodCategories(Long restaurantId, Set<FoodCategory> foodCategories) {
        this.getRestaurantById(restaurantId, false);
        List<RestaurantFoodCategoryMappingEntity> categoryMappingEntities = new ArrayList<>();
        foodCategories.forEach(foodCategory -> {
            RestaurantFoodCategoryMappingEntity restaurantFoodCategoryMapping =
                    new RestaurantFoodCategoryMappingEntity(null,restaurantId,foodCategory);
            categoryMappingEntities.add(restaurantFoodCategoryMapping);
        });
        restaurantFoodCategoryMappingRepository.saveAll(categoryMappingEntities);
    }

    @Override
    public Long saveItemToMaster(Item item, Long restaurantId) {
        this.getRestaurantById(restaurantId, false);
        ItemEntity itemEntity = CustomRestaurantMapper.map(item);
        return itemRepository.save(itemEntity).getId();
    }
}
