package com.proje.salad_App.entity.enums;

public enum SeedType {
    CHIA("Chia Seeds", 1, "25 gr (1 piece)", 1.5),
    FLAX("Flax Seeds", 1, "25 gr (1 piece)", 1.0),
    SESAME("Sesame Seeds", 1, "25 gr (1 piece)", 1.25),
    SUNFLOWER("Sunflower Seeds", 1, "25 gr (1 piece)", 1.0),
    PUMPKIN("Pumpkin Seeds", 1, "25 gr (1 piece)", 1.25),
    HEMP("Hemp Seeds", 1, "25 gr (1 piece)", 1.75),
    POPPY("Poppy Seeds", 1, "25 gr (1 piece)", 1.1),
    QUINOA("Quinoa Seeds", 1, "25 gr (1 piece)", 2.0),
    ALMOND("Almond Slices", 1, "25 gr (1 piece)", 1.5),
    WALNUT("Walnut Pieces", 1, "25 gr (1 piece)", 1.75);

    private final String displayName;
    private final int amount;
    private final String unit;
    private final double price;

    SeedType(String displayName, int amount, String unit, double price) {
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
