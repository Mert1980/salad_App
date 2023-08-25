package com.proje.salad_App.entity.enums;

public enum GrainType {
    BULGUR("Bulgur", 1, "piece (100 gr)", 1.0),
    PASTA("Pasta", 1, "piece (100 gr)", 2.0),
    OATS("Oats", 1, "piece (100 gr)", 1.0),
    BOILED_WHEAT("Boiled Wheat", 1, "piece (100 gr)", 1.5),
    GROUND_OATS("Ground Oats", 1, "piece (100 gr)", 1.5),
    BARLEY("Barley", 1, "piece (100 gr)", 2.0),
    WHOLE_WHEAT_PASTA("Whole Wheat Pasta", 1, "piece (100 gr)", 3.0),
    RICE("Rice", 1, "piece (100 gr)", 2.0),
    BLACK_RICE("Black Rice", 1, "piece (100 gr)", 3.5),
    COUSCOUS("Couscous", 1, "piece (100 gr)", 3.0);

    private final String displayName;
    private final int amount;
    private final String unit;
    private final double price;

    GrainType(String displayName, int amount, String unit, double price) {
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
