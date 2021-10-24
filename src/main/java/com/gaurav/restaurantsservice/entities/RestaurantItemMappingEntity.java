package com.gaurav.restaurantsservice.entities;

import com.gaurav.restaurantsservice.enums.ServeType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class RestaurantItemMappingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Column(name = "ratings")
    private String ratings;

    @Column(name = "avg_preparation_time", nullable = false)
    private BigDecimal avgPreparationTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "serve_type", nullable = false)
    private ServeType serveType;

    @Column(name = "serve_quantity", nullable = false)
    private BigDecimal serveQuantity;

    @Column(name = "price", nullable = false)
    private BigDecimal price;


}
