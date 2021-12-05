package com.gaurav.restaurantsservice.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;

@Entity(name = "order_history_details")
@Getter
@Setter
public class OrderHistoryDetailsEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_history_id")
    private Long orderHistoryId; // column(orderHistoryId, itemServedId) must be unique in this table

    @Column(name = "item_served_id")
    private Long itemServedId;

    @Column(name = "order_description")
    private String orderDescription;

    @Column(name= "quantity")
    private BigInteger quantity;

    @Column(name = "amount")
    private BigDecimal amount;

}
