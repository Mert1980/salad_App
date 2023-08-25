package com.proje.salad_App.entity.enums;

public enum VegetableType { TOMATO("Tomato", 1.0, "piece", 0.5),
    CUCUMBER("Cucumber", 1.0, "piece", 0.4),
    CARROT("Carrot", 1.0, "piece", 0.3),
    RED_PEPPER("Red Pepper", 1.0, "piece", 0.6),
    GREEN_PEPPER("Green Pepper", 1.0, "piece", 0.6),
    ONION("Onion", 1.0, "piece", 0.4),
    AVOCADO("Avocado", 1.0, "piece", 1.0),
    RADISH("Radish", 2.0, "pieces", 0.3),
    OLIVE("Olive", 5.0, "pieces", 0.5),
    CORN("Corn", 1.0, "piece", 0.7),
    LETTUCE("Lettuce", 100.0, "gr", 0.8),
    SPINACH("Spinach", 100.0, "gr", 0.9),
    ARUGULA("Arugula", 100.0, "gr", 0.7),
    CABBAGE("Cabbage", 100.0, "gr", 0.6);

    private final String displayName;
    private final double amount;
    private final String unit;
    private final double price;

    VegetableType(String displayName, double amount, String unit, double price) {
        this.displayName = displayName;
        this.amount = amount;
        this.unit = unit;
        this.price = price;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public double getPrice() {
        return price;
    }
}
