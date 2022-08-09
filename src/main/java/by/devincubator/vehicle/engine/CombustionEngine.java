package by.devincubator.vehicle.engine;

public abstract class CombustionEngine extends AbstractEngine {
    private double engineCapacity;
    private double fuelTankCapacity;
    private double fuelConsumptionPer100;

    public CombustionEngine(String typeName, double taxCoefficient, double engineCapacity, double fuelTankCapacity, double fuelConsumptionPer100) {
        super(typeName, taxCoefficient);
        this.engineCapacity = engineCapacity;
        this.fuelTankCapacity = fuelTankCapacity;
        this.fuelConsumptionPer100 = fuelConsumptionPer100;
    }

    public double getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(double engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public double getFuelTankCapacity() {
        return fuelTankCapacity;
    }

    public void setFuelTankCapacity(double fuelTankCapacity) {
        this.fuelTankCapacity = fuelTankCapacity;
    }

    public double getFuelConsumptionPer100() {
        return fuelConsumptionPer100;
    }

    public void setFuelConsumptionPer100(double fuelConsumptionPer100) {
        this.fuelConsumptionPer100 = fuelConsumptionPer100;
    }

    @Override
    public double getTaxPerMonth() {
        return getTaxCoefficient();
    }

    @Override
    public double getMaxKilometers() {
        return fuelTankCapacity * 100 / fuelConsumptionPer100;
    }
}
