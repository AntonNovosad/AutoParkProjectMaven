package by.devincubator.service;

import by.devincubator.exception.DefectedVehicleException;
import by.devincubator.vehicle.Rent;
import by.devincubator.vehicle.Vehicle;

import java.util.Date;

public class RentVehicle {
    public Rent rentVehicle(Vehicle vehicle, MechanicService mechanicService, double price) throws DefectedVehicleException {
        if (mechanicService.isBroken(vehicle)) {
            throw new DefectedVehicleException("Vehicle is broken");
        }
        return new Rent(vehicle.getId(), new Date(), price);
    }
}
