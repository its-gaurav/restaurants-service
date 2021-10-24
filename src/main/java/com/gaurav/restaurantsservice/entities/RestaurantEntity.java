package com.gaurav.restaurantsservice.entities;

import com.gaurav.restaurantsservice.domains.Item;
import com.gaurav.restaurantsservice.enums.DiningCategory;
import com.gaurav.restaurantsservice.enums.FoodCategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "restaurant")
@Getter
@Setter
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
//    private List<Item> itemsServed;
    @Column(name = "opens_at")
    private String opensAt;
    @Column(name = "closes_at")
    private String closesAt;
    @Column(name = "dining_category")
    private DiningCategory diningCategory;
}
