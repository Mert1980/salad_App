package com.proje.salad_App.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaladResponse {
    private Long id;
    private String name;
    private Set<IngredientResponse> ingredients;
}
