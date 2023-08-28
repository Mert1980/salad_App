package com.proje.salad_App.exeption;

public class DuplicateAdminException extends RuntimeException{
    public DuplicateAdminException(String message) {
        super(message);
    }
}
