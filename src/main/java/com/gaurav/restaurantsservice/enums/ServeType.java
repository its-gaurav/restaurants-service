package com.gaurav.restaurantsservice.enums;

public enum ServeType {

    PIECE("piece"),
    GRAM("gram"),
    KILOGRAM("kilo-gram"),
    MILILTR("milli-litre"),
    LITRE("litre");

    private final String description;

    ServeType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
