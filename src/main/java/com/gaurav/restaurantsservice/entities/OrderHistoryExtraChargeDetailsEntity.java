package com.gaurav.restaurantsservice.entities;

import com.gaurav.restaurantsservice.enums.ExtraChargeType;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "order_history_extra_charge_details")
public class OrderHistoryExtraChargeDetailsEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_history_id")
    private Long orderHistoryId;

    @Column(name = "extra_charge_type")
    @Enumerated(EnumType.STRING)
    private ExtraChargeType extraChargeType;

    @Column(name = "amount")
    private BigDecimal amount;
}
