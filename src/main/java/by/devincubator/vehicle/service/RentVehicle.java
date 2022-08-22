package by.devincubator.vehicle.service;

import by.devincubator.entity.Rents;
import by.devincubator.entity.Vehicles;
import by.devincubator.exception.DefectedVehicleException;

import java.util.Date;

public class RentVehicle {
    public Rents rentVehicle(Vehicles vehicle, MechanicService mechanicService, double price) throws DefectedVehicleException {
        if (mechanicService.isBroken(vehicle)) {
            throw new DefectedVehicleException("Vehicle is broken");
        }
        return new Rents(1l, vehicle.getId(), new Date(), price);
    }
}
