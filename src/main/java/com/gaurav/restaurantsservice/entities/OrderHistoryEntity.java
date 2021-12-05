package com.gaurav.restaurantsservice.entities;

import com.gaurav.restaurantsservice.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "order_history")
@Getter
@Setter
public class OrderHistoryEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_no", nullable = false, unique = true)
    private String orderNumber;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "delivery_executive_id")
    private Long deliveryExecutiveId;

    @Column(name = "initial_amount")
    private BigDecimal initialAmount;

    @Column(name = "coupon_code_applied")
    private String couponCodeApplied;

    @Column(name = "discount_amount")
    private BigDecimal discountAmount;

    @Column(name = "final_amount")
    private BigDecimal finalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "ordered_on")
    private LocalDateTime orderedOn;

}
