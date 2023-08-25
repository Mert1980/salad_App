package com.proje.salad_App.entity.enums;

public enum MeatType {
    GRILLED_BEEF("Grilled Beef", 1, "piece (50 gr)", 10.0),
    GRILLED_CHICKEN("Grilled Chicken", 1, "piece (50 gr)", 8.0),
    GRILLED_FISH("Grilled Fish", 1, "piece (50 gr)", 12.0),
    SMOKED_SALAMI("Smoked Salami", 1, "piece (50 gr)", 6.0),
    TURKEY_SAUSAGE("Turkey Sausage", 1, "piece", 2.5),
    BEEF_SAUSAGE("Beef Sausage", 1, "piece", 3.0),
    BOILED_CHICKEN_MEAT("Boiled Chicken Meat", 1, "piece (50 gr)", 7.0),
    BOILED_TURKEY_MEAT("Boiled Turkey Meat", 1, "piece (50 gr)", 8.5);

    private final String displayName;
    private final int amount; // Bir adet olarak ifade ediliyor
    private final String unit; // Miktar veya ölçü birimini temsil eden bir dize
    private final double price;

    MeatType(String displayName, int amount, String unit, double price) {
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
