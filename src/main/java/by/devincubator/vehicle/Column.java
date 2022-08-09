package by.devincubator.vehicle;

public enum Column {
    ID("Id"),
    TYPE("Type"),
    MODEL_NAME("ModelName"),
    NUMBER("Number"),
    WEIGHT("Weight (kg)"),
    YEAR("Year"),
    MILEAGE("Mileage"),
    COLOR("Color"),
    INCOME("Income"),
    TAX("Tax"),
    PROFIT("Profit"),
    TOTAL("Total");

    private final String name;

    Column(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

