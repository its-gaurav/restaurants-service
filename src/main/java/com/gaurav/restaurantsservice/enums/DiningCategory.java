package com.gaurav.restaurantsservice.enums;

public enum DiningCategory {

    DINE_IN("Dining In"),
    TAKE_AWAY("Take Away"),
    BOTH("Dining In / Take Away");

    private final String description;

    DiningCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
