package com.gaurav.restaurantsservice.enums;

public enum ServeType {

    PIECE("Piece"),
    GRAM("Gram"),
    KILOGRAM("Kilo-gram"),
    MILILTR("Milli-litre"),
    LITRE("Litre");

    private final String description;

    ServeType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
