package com.gaurav.restaurantsservice.domains;

import com.gaurav.restaurantsservice.enums.FoodCategory;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class Item {
    private Long id;
    @NotNull @NotBlank
    private String name;
    @NotNull
    private FoodCategory foodCategory;
}
