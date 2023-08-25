package com.proje.salad_App.payload.response;

import com.proje.salad_App.entity.concretes.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockResponse {
    private Ingredient ingredient;
    private int quantity;
    private String unit;

}

