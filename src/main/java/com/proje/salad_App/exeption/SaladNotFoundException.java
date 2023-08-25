package com.proje.salad_App.exeption;

public class SaladNotFoundException extends RuntimeException {
    public SaladNotFoundException(String message) {
        super(message);
    }
}
