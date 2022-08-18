package by.devincubator.service;

import by.devincubator.vehicle.Vehicle;

import java.util.HashMap;
import java.util.Map;

public class BadMechanicService implements Fixer {

    public BadMechanicService() {
    }

    @Override
    public Map<String, Integer> detectBreaking(Vehicle vehicle) {
        return new HashMap<>();
    }

    @Override
    public void repair(Vehicle vehicle) {

    }

    @Override
    public boolean isBroken(Vehicle vehicle) {
        return false;
    }
}
