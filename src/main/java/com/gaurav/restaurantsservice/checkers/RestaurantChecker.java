package com.gaurav.restaurantsservice.checkers;

import com.gaurav.restaurantsservice.domains.OrderDetails;
import com.gaurav.restaurantsservice.domains.User;
import com.gaurav.restaurantsservice.entities.RestaurantFoodCategoryMappingEntity;
import com.gaurav.restaurantsservice.entities.RestaurantItemMappingEntity;
import com.gaurav.restaurantsservice.enums.FoodCategory;
import com.gaurav.restaurantsservice.exceptions.*;
import com.gaurav.restaurantsservice.repositories.RestaurantFoodCategoryMappingRepository;
import com.gaurav.restaurantsservice.repositories.RestaurantItemMappingRepository;
import com.gaurav.restaurantsservice.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

import static com.gaurav.restaurantsservice.ExceptionConstants.*;

//@Component // this is being loaded into spring ioc container through @Bean annotation
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
        List<ServiceException> serviceExceptions = new ArrayList<>();
        foodCategories.forEach(category-> {
            if(categoriesAlreadyMapped.stream().map(RestaurantFoodCategoryMappingEntity::getFoodCategory).
                    collect(Collectors.toList()).contains(category)){
                serviceExceptions.add(new ServiceException(category.getDescription()+" already exists"));
            }
        });
        if(!CollectionUtils.isEmpty(serviceExceptions)){
            throw new CategoryAlreadyExistException(serviceExceptions.stream().
                    map(ServiceException::getMessage).collect(Collectors.toList()));
        }
    }
    /*
    * @param orderDetails
    * @param restaurantId
    *
    * Checks if restaurantId is valid
    * Checks if provided itemIds are valid
    * Checks if provided itemIds are available
    * */
    public void checkValidOrderDetails(OrderDetails orderDetails, Long restaurantId) {
        if(orderDetails.getItemsOrdered().isEmpty()){
            throw BusinessLogicException.badRequest(Collections.singletonList(new ServiceException("Order Details Missing")));
        }
        // check valid userId
//         A way to make call from 1 service to another
        HashMap<String,Long> params = new HashMap<>();
        params.put("userId", orderDetails.getUserId());
        try {
            ResponseEntity<User> response = new RestTemplate().getForEntity("http://localhost:8080/users/{userId}", User.class, params);
        }catch (Exception ex){
            throw new InvalidUserIdException("User Id "+orderDetails.getUserId()+" Not Found");
        }

        restaurantRepository.findById(restaurantId).
                orElseThrow(() -> new RestaurantNotFoundException(RESTAURANT_NOT_FOUND));
        orderDetails.setRestaurantId(restaurantId);
        Set<Long> orderedItemsMappingIds = orderDetails.getItemsOrdered().keySet();
        if(!CollectionUtils.isEmpty(orderedItemsMappingIds)) {
            List<RestaurantItemMappingEntity> itemMappingEntities = restaurantItemMappingRepository.findByIdIn(orderedItemsMappingIds);
            orderedItemsMappingIds.forEach(orderedItemId-> {
               if(itemMappingEntities.stream().noneMatch(itemMapped -> itemMapped.getId().equals(orderedItemId))){
                   throw new ItemMappingNotFoundException(ITEM_SERVED_NOT_FOUND+" "+orderedItemId);
               }
                if(itemMappingEntities.stream().anyMatch(itemMapped -> !itemMapped.getRestaurantId().equals(restaurantId))){
                    throw new InvalidItemMappingIdException(ITEM_SERVED_ID_INVALID+": restaurantId "+restaurantId);
                }
            });
            itemMappingEntities.forEach(itemMapped -> {
                if(!itemMapped.isAvailable()){
                    throw new OutOfStockException(ITEM_OUT_OF_STOCK+": "+itemMapped.getId());
                }
            });
        }else{
            throw new OrderDetailsNotValidException(ORDER_DETAILS_INVALID);
        }
    }
}
