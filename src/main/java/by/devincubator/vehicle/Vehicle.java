package by.devincubator.vehicle;

import by.devincubator.service.TechnicalSpecialist;
import by.devincubator.vehicle.engine.Startable;
import by.devincubator.exception.NotVehicleException;

import java.util.List;
import java.util.Objects;

public class Vehicle implements Comparable<Vehicle> {
    private int id;
    private VehicleType type;
    private String modelName;
    private String registrationNumber;
    private int weightKg;
    private int manufactureYear;
    private int mileage;
    private Color color;
    private Startable engine;
    private List<Rent> rentList;

    public Vehicle() {
    }

    public Vehicle(int id,
                   VehicleType type,
                   String modelName,
                   String registrationNumber,
                   int weightKg,
                   int manufactureYear,
                   int mileage,
                   Color color,
                   Startable engine,
                   List<Rent> rentList) {
        this.id = id;
        this.type = type;
        this.modelName = modelName;
        this.registrationNumber = registrationNumber;
        this.weightKg = weightKg;
        this.manufactureYear = manufactureYear;
        this.mileage = mileage;
        this.color = color;
        this.engine = engine;
        this.rentList = rentList;
    }

    public Vehicle(VehicleType type,
                   String modelName,
                   String registrationNumber,
                   int weightKg,
                   int manufactureYear,
                   int mileage,
                   Color color,
                   Startable engine) {
        try {
            if (!TechnicalSpecialist.validateVehicleType(type)) {
                throw new NotVehicleException("Type " + type);
            } else {
                this.type = type;
            }
            if (!TechnicalSpecialist.validateModelName(modelName)) {
                throw new NotVehicleException("Model name " + modelName);
            } else {
                this.modelName = modelName;
            }
            if (!TechnicalSpecialist.validateRegistrationNumber(registrationNumber)) {
                throw new NotVehicleException("Registration number " + registrationNumber);
            } else {
                this.registrationNumber = registrationNumber;
            }
            if (!TechnicalSpecialist.validateWeight(weightKg)) {
                throw new NotVehicleException("Weight " + weightKg);
            } else {
                this.weightKg = weightKg;
            }
            if (!TechnicalSpecialist.validateManufactureYear(manufactureYear)) {
                throw new NotVehicleException("Manufacture year " + manufactureYear);
            } else {
                this.manufactureYear = manufactureYear;
            }
            if (!TechnicalSpecialist.validateMileage(mileage)) {
                throw new NotVehicleException("Mileage " + mileage);
            } else {
                this.mileage = mileage;
            }
            if (!TechnicalSpecialist.validateColor(color)) {
                throw new NotVehicleException("Color " + color);
            } else {
                this.color = color;
            }
            this.engine = engine;
        } catch (NotVehicleException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Rent> getRentList() {
        return rentList;
    }

    public void setRentList(List<Rent> rentList) {
        this.rentList = rentList;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        if (TechnicalSpecialist.validateVehicleType(type)) {
            this.type = type;
        }
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        if (TechnicalSpecialist.validateModelName(modelName)) {
            this.modelName = modelName;
        }
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        if (TechnicalSpecialist.validateRegistrationNumber(registrationNumber)) {
            this.registrationNumber = registrationNumber;
        }
    }

    public int getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(int weightKg) {
        if (TechnicalSpecialist.validateWeight(weightKg)) {
            this.weightKg = weightKg;
        }
    }

    public int getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(int manufactureYear) {
        if (TechnicalSpecialist.validateManufactureYear(manufactureYear)) {
            this.manufactureYear = manufactureYear;
        }
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        if (TechnicalSpecialist.validateMileage(mileage)) {
            this.mileage = mileage;
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        if (TechnicalSpecialist.validateColor(color)) {
            this.color = color;
        }
    }

    public Startable getEngine() {
        return engine;
    }

    public void setEngine(Startable engine) {
        this.engine = engine;
    }

    public Double getCalcTaxPerMonth() {
        return (weightKg * 0.0013) + (type.getTaxCoefficient() * engine.getTaxPerMonth() * 30) + 5;
    }

    public double getTotalIncome() {
        double common = 0;
        for (Rent r : rentList) {
            common += r.getPrice();
        }
        return common;
    }

    public double getTotalProfit() {
        return getTotalIncome() - getCalcTaxPerMonth();
    }

    @Override
    public String toString() {
        return id + ", " +
                type + ", " +
                modelName + ", " +
                registrationNumber + ", " +
                weightKg + ", " +
                manufactureYear + ", " +
                mileage + ", " +
                color + ", " +
                engine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(type, vehicle.type) && Objects.equals(modelName, vehicle.modelName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, modelName);
    }

    public int compareTo(Vehicle obj) {
        if (manufactureYear < obj.manufactureYear) {
            return -1;
        } else if (manufactureYear > obj.manufactureYear) {
            return 1;
        } else {
            if (mileage < obj.mileage) {
                return -1;
            } else if (mileage > obj.mileage) {
                return 1;
            }
        }
        return 0;
    }
}