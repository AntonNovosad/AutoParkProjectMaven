package by.devincubator.vehicle.service;

import by.devincubator.entity.Vehicles;

import java.util.Map;

public interface Fixer {
    Map<String, Integer> detectBreaking(Vehicles vehicle);
    void repair(Vehicles vehicle);
    boolean isBroken(Vehicles vehicle);

    default boolean detectAndRepair(Vehicles vehicle) {
        detectBreaking(vehicle);
        if (isBroken(vehicle)) {
            repair(vehicle);
            return true;
        }
        return false;
    }
}
