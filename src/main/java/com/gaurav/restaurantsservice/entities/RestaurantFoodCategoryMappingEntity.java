package com.gaurav.restaurantsservice.entities;

import com.gaurav.restaurantsservice.enums.FoodCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "restaurant_food_category_mapping")
@Table(uniqueConstraints = { @UniqueConstraint(name = "uniqueRestaurantIdAndFoodCategory", columnNames = {"restaurant_id","food_category"}) })
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantFoodCategoryMappingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "restaurant_id",nullable = false)
    private Long restaurantId;

    @Enumerated(EnumType.STRING)
    @Column(name = "food_category",nullable = false)
    private FoodCategory foodCategory;
}
