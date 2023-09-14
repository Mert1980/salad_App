package com.proje.salad_App.payload.response;

import com.fasterxml.jackson.databind.deser.UnresolvedId;
import com.proje.salad_App.entity.concretes.User;
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
    private double price;

}
