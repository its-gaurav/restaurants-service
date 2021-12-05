package com.gaurav.restaurantsservice.repositories;

import com.gaurav.restaurantsservice.entities.RestaurantItemMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RestaurantItemMappingRepository extends JpaRepository<RestaurantItemMappingEntity,Long> {

    Optional<RestaurantItemMappingEntity> findById(Long id);

    List<RestaurantItemMappingEntity> findByRestaurantId(Long restaurantId);

    List<RestaurantItemMappingEntity> findByIdIn(Set<Long> orderedItemsMappingIds);
}
