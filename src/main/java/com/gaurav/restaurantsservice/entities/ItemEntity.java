package com.gaurav.restaurantsservice.entities;

import com.gaurav.restaurantsservice.enums.FoodCategory;

import javax.persistence.*;

@Entity(name = "item")
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "food_category")
    private FoodCategory foodCategory;
}
