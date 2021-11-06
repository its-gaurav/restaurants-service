package com.gaurav.restaurantsservice.repositories;

import com.gaurav.restaurantsservice.entities.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity,Long> {

    Optional<RestaurantEntity> findById(Long restaurantId);



}
