package com.proje.salad_App.payload.request;

import com.proje.salad_App.entity.enums.IngredientType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientRequest {
    @NotBlank(message = "Please enter name")
    private String name;

    @NotNull(message = "Please enter price")
    private double price;

    @NotNull(message = "Please select ingredient type")
    private IngredientType ingredientType;
}
