package com.gaurav.restaurantsservice.repositories;

import com.gaurav.restaurantsservice.entities.OrderHistoryDetailsEntity;
import com.gaurav.restaurantsservice.entities.OrderHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderHistoryDetailsRepository extends JpaRepository<OrderHistoryDetailsEntity,Long> {

    List<OrderHistoryDetailsEntity> findByOrderHistoryId(Long orderHistoryId);



}
