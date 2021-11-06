package com.gaurav.restaurantsservice.repositories;

import com.gaurav.restaurantsservice.entities.RestaurantFoodCategoryMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantFoodCategoryMappingRepository extends JpaRepository<RestaurantFoodCategoryMappingEntity,Long> {

    List<RestaurantFoodCategoryMappingEntity> findByRestaurantId(Long restaurantId);

    List<RestaurantFoodCategoryMappingEntity> findByRestaurantIdIn(List<Long> restaurantIdList);
}
