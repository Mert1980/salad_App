package com.proje.salad_App.entity.enums;

public enum SauceType {
    RANCH("Ranch", 10, "gr (1 piece)", 1.5),
    CAESAR("Caesar", 10, "gr (1 piece)", 1.75),
    ITALIAN("Italian", 10, "gr (1 piece)", 1.25),
    BALSAMIC("Balsamic", 10, "gr (1 piece)", 1.0),
    THOUSAND_ISLAND("Thousand Island", 10, "gr (1 piece)", 1.5),
    HONEY_MUSTARD("Honey Mustard", 10, "gr (1 piece)", 1.25),
    TAHINI("Tahini", 10, "gr (1 piece)", 1.0),
    YOGURT_DILL("Yogurt Dill", 10, "gr (1 piece)", 1.5),
    BLUE_CHEESE("Blue Cheese", 10, "gr (1 piece)", 1.75),
    SESAME_SOY("Sesame Soy", 10, "gr (1 piece)", 1.25);

    private final String displayName;
    private final int amount;
    private final String unit;
    private final double price;

    SauceType(String displayName, int amount, String unit, double price) {
        this.displayName = displayName;
        this.amount = amount;
        this.unit = unit;
        this.price = price;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public double getPrice() {
        return price;
    }
}
