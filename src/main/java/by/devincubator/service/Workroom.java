package by.devincubator.service;

import by.devincubator.infrastructure.core.annotations.Autowired;
import by.devincubator.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class Workroom {
    @Autowired
    private Fixer mechanic;

    public Workroom() {
    }

    public Fixer getMechanic() {
        return mechanic;
    }

    public void setMechanic(Fixer mechanic) {
        this.mechanic = mechanic;
    }

    public void checkAllVehicle(List<Vehicle> vehicles) {
        List<Vehicle> vehicleList = new ArrayList<>();
        vehicles.stream().forEach(vehicle -> checkBrokenVehicle(vehicle, vehicleList));
        System.out.println("Cars are not broken: " + vehicleList);
    }

    private void checkBrokenVehicle(Vehicle vehicle, List<Vehicle> vehicleList) {
        if (mechanic.isBroken(vehicle)) {
            System.out.println("Car " + vehicle.getId() + " broken.");
        } else {
            vehicleList.add(vehicle);
        }
    }
}
