package by.devincubator.vehicle;

import java.util.Date;

public class Rent {
    private int vehicleId;
    private Date date;
    private double price;

    public Rent(int vehicleId, Date date, double price) {
        this.vehicleId = vehicleId;
        this.date = date;
        this.price = price;
    }

    public Rent() {
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Rent{" +
                "date=" + date +
                ", price=" + price +
                '}';
    }
}
