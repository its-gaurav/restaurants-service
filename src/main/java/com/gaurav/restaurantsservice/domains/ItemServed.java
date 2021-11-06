package com.gaurav.restaurantsservice.domains;

import com.gaurav.restaurantsservice.enums.FoodCategory;
import com.gaurav.restaurantsservice.enums.ServeType;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@AllArgsConstructor
public class ItemServed {

    private Long mappingId;
    private String name;
    private FoodCategory foodCategory;
    @Positive
    private BigDecimal price;
    @NotNull
    private Long restaurantId;
    @NotNull
    private Long itemId;
    private String ratings;
    @Positive
    private BigDecimal avgPreparationTime;
    @NotNull
    private ServeType serveType;
    @Positive
    private BigInteger serveQuantity;

    private boolean isAvailable;

}
