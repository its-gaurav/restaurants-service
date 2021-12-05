package com.gaurav.restaurantsservice.enums;

public enum ExtraChargeType {
    DELIVERY("Delivery"),
    TAXES("Taxes"),
    PACKING("Packing"),
    EXTRA_SURGE("Extra Surge");

    private final String description;

    ExtraChargeType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
