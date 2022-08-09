package by.devincubator.vehicle.engine;

public class GasolineEngine extends CombustionEngine {
    private static final String TYPE_NAME = "Gasoline";
    private static final double TAX_COEFFICIENT = 1.1;

    public GasolineEngine(double engineCapacity, double fuelTankCapacity, double fuelConsumptionPer100) {
        super(TYPE_NAME, TAX_COEFFICIENT, engineCapacity, fuelTankCapacity, fuelConsumptionPer100);
    }
}
