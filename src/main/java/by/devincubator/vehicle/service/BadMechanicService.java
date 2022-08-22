package by.devincubator.vehicle.service;

import by.devincubator.entity.Vehicles;

import java.util.HashMap;
import java.util.Map;

public class BadMechanicService implements Fixer {

    public BadMechanicService() {
    }

    @Override
    public Map<String, Integer> detectBreaking(Vehicles vehicle) {
        return new HashMap<>();
    }

    @Override
    public void repair(Vehicles vehicle) {

    }

    @Override
    public boolean isBroken(Vehicles vehicle) {
        return false;
    }
}
