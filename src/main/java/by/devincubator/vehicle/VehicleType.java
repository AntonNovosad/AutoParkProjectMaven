package by.devincubator.vehicle;

public class VehicleType {
    private int id;
    private String name;
    private double taxCoefficient;

    public VehicleType() {
    }

    public VehicleType(String name, double taxCoefficient) {
        this.name = name;
        this.taxCoefficient = taxCoefficient;
    }

    public VehicleType(int id, String name, double taxCoefficient) {
        this.id = id;
        this.name = name;
        this.taxCoefficient = taxCoefficient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTaxCoefficient() {
        return taxCoefficient;
    }

    public void setTaxCoefficient(double taxCoefficient) {
        this.taxCoefficient = taxCoefficient;
    }

    public String display() {
        return "Type name = " + name + '\n' +
                "Tax coefficient = " + taxCoefficient;
    }

    @Override
    public String toString() {
        return name + "," + '\"' + taxCoefficient + '\"';
    }
}
