package by.devincubator.vehicle.engine;

public class ElectricalEngine extends AbstractEngine {
    private static final String TYPE_NAME = "Electrical";
    private static final double TAX_COEFFICIENT = 0.1;

    private double batterySize;
    private double electricityConsumption;

    public ElectricalEngine(double batterySize, double electricityConsumption) {
        super(TYPE_NAME, TAX_COEFFICIENT);
        this.batterySize = batterySize;
        this.electricityConsumption = electricityConsumption;
    }

    public double getBatterySize() {
        return batterySize;
    }

    public void setBatterySize(double batterySize) {
        this.batterySize = batterySize;
    }

    public double getElectricityConsumption() {
        return electricityConsumption;
    }

    public void setElectricityConsumption(double electricityConsumption) {
        this.electricityConsumption = electricityConsumption;
    }


    @Override
    public double getTaxPerMonth() {
        return getTaxCoefficient();
    }

    @Override
    public double getMaxKilometers() {
        return batterySize / electricityConsumption;
    }
}
