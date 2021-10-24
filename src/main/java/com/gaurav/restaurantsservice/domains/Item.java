package com.gaurav.restaurantsservice.domains;

import com.gaurav.restaurantsservice.enums.FoodCategory;

import java.math.BigDecimal;

public class Item {

    private Long id;
    private String name;
    private FoodCategory foodCategory;
    private BigDecimal price;

}
