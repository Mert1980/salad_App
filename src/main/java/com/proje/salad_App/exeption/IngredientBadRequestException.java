package com.proje.salad_App.exeption;

public class IngredientBadRequestException extends RuntimeException {
    public IngredientBadRequestException(String message) {
        super(message);
    }
}
