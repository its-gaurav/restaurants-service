package com.gaurav.restaurantsservice.domains;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class OrderDetails {

    @NotNull
    private Long userId;
    private Long restaurantId;
    // key reflects itemMappingId of restaurant and value reflects order-quantity of that item
    private Map<Long, BigInteger> itemsOrdered = new HashMap<>();

    private Map<String, BigDecimal> itemsOrderedDetails = new HashMap<>();

    private Map<String, BigDecimal> extraChargeDetails = new HashMap<>();

    private BigDecimal initialTotal = BigDecimal.ZERO; // includes totalItemValue + deliveryCharge + extraCharges
    private String couponCode;
    private BigDecimal couponDiscount;
    private BigDecimal finalTotal = BigDecimal.ZERO; // (initialTotal - discount/coupon Applied)

    // coupon applicable


}
