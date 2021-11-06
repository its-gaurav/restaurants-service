package com.gaurav.restaurantsservice.domains;

import com.gaurav.restaurantsservice.enums.DiningCategory;
import com.gaurav.restaurantsservice.enums.FoodCategory;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
public class Restaurant {

    private Long id;
    @NotNull(message = "Please provide restaurant name") @NotBlank(message = "Please provide restaurant name")
    @Size(min = 3)
    private String name;
    @Size(min = 5)
    private String address;
    private List<FoodCategory> foodCategories;
    private List<ItemServed> itemsServed;
    @NotNull @NotBlank
    private String opensAt;
    @NotNull @NotBlank
    private String closesAt;
    @NotNull(message = "Dining category is required")
    private DiningCategory diningCategory;
    private boolean isClosed;

    public Restaurant(Long id, String name, String address, String opensAt, String closesAt, DiningCategory diningCategory) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.opensAt = opensAt;
        this.closesAt = closesAt;
        this.diningCategory = diningCategory;
    }
}
