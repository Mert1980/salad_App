package com.proje.salad_App.payload.response;

import com.proje.salad_App.entity.enums.IngredientType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientResponse {
    private String name;
    private double price;
    private IngredientType ingredientType;


}
