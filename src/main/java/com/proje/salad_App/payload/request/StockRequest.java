package com.proje.salad_App.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockRequest {
    @NotNull(message = "Please enter ingredient ID")
    private Long ingredientId;

    @NotNull(message = "Please enter quantity")
    private int quantity;

    @NotBlank(message = "Please enter unit")
    private String unit;
}
