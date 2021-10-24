package com.gaurav.restaurantsservice.domains;

import com.gaurav.restaurantsservice.enums.DiningCategory;
import com.gaurav.restaurantsservice.enums.FoodCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@AllArgsConstructor
public class Restaurant {

    private Long id;
    private String name;
    private String address;
    private List<FoodCategory> foodCategories;
    private List<Item> itemsServed;
    private String opensAt;
    private String closesAt;
    private DiningCategory diningCategory;

}
