package com.gaurav.restaurantsservice.entities;

import com.gaurav.restaurantsservice.enums.FoodCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "items")
@Data
@AllArgsConstructor
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "food_category", nullable = false)
    private FoodCategory foodCategory;

    public ItemEntity() {
    }
}
