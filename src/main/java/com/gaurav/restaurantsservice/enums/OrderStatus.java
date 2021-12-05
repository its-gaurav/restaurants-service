package com.gaurav.restaurantsservice.enums;

public enum OrderStatus {
    PENDING("Pending") ,
    REJECTED("Rejected"),
    IN_PRPRTN("In Preparation"),
    PREPARED("Prepared"),
    DLVRY_EXCTV_ASSGND("Delivery Executive Assigned"),
    PICKED("Picked"), IN_TRANSIT("In Transit"),
    DELIVERED("Delivered");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
