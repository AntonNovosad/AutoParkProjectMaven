package by.devincubator.vehicle.service;

import by.devincubator.entity.Vehicles;
import by.devincubator.infrastructure.core.annotations.Autowired;

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

    public void checkAllVehicle(List<Vehicles> vehicles) {
        List<Vehicles> vehicleList = new ArrayList<>();
        vehicles.stream().forEach(vehicle -> checkBrokenVehicle(vehicle, vehicleList));
        System.out.println("Cars are not broken: " + vehicleList);
    }

    private void checkBrokenVehicle(Vehicles vehicle, List<Vehicles> vehicleList) {
        if (mechanic.isBroken(vehicle)) {
            System.out.println("Car " + vehicle.getId() + " broken.");
        } else {
            vehicleList.add(vehicle);
        }
    }
}
