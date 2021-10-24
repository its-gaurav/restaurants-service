package com.gaurav.restaurantsservice.enums;

public enum FoodCategory{

    SOUTH_INDN("South Indian"),
    CHINESE("Chinese"),
    STRT_FOOD("Street Food"),
    NORTH_INDN("North Indian"),
    DESSRTS("Desserts"),
    BVRGS("Beverages");

    private final String description;

    FoodCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
