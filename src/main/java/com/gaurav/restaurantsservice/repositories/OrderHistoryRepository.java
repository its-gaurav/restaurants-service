package com.gaurav.restaurantsservice.repositories;

import com.gaurav.restaurantsservice.entities.OrderHistoryEntity;
import com.gaurav.restaurantsservice.entities.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistoryEntity,Long> {

    Optional<OrderHistoryEntity> findById(Long Id);
//    Optional<OrderHistoryEntity> findTopOrderByIdDesc();
}
