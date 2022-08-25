package by.devincubator.vehicle.service;

import by.devincubator.entity.Vehicles;
import by.devincubator.exception.DefectedVehicleException;
import by.devincubator.vehicle.Rent;

import java.util.Date;

public class RentVehicle {
    public Rent rentVehicle(Vehicles vehicle, MechanicService mechanicService, double price) throws DefectedVehicleException {
        if (mechanicService.isBroken(vehicle)) {
            throw new DefectedVehicleException("Vehicle is broken");
        }
        return new Rent(1, new Date(), price);
    }
}
