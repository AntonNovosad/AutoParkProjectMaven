package by.devincubator.vehicle.engine;

public class DieselEngine extends CombustionEngine {
    private static final String TYPE_NAME = "Diesel";
    private static final double TAX_COEFFICIENT = 1.2;

    public DieselEngine(double engineCapacity, double fuelTankCapacity, double fuelConsumptionPer100) {
        super(TYPE_NAME, TAX_COEFFICIENT, engineCapacity, fuelTankCapacity, fuelConsumptionPer100);
    }
}
