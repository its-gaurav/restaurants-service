package com.gaurav.restaurantsservice.entities;

import com.gaurav.restaurantsservice.enums.DiningCategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "restaurants")
@Getter
@Setter
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "opens_at", nullable = false)
    private String opensAt;
    @Column(name = "closes_at",nullable = false)
    private String closesAt;
    @Enumerated(EnumType.STRING)
    @Column(name = "dining_category", nullable = false)
    private DiningCategory diningCategory;
    @Column(name = "is_closed")
    private boolean isClosed;
}
