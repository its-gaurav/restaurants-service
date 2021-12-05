package com.gaurav.restaurantsservice.repositories;

import com.gaurav.restaurantsservice.entities.OrderHistoryExtraChargeDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderHistoryExtraChargeDetailsRepository extends JpaRepository<OrderHistoryExtraChargeDetailsEntity,Long> {

    List<OrderHistoryExtraChargeDetailsEntity> findByOrderHistoryId(Long orderHistoryId);

}
