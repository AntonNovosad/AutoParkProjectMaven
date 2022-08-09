package by.devincubator.vehicle.comparator;

import by.devincubator.service.MechanicService;
import by.devincubator.vehicle.Vehicle;

import java.util.Comparator;

public class ComparatorByDefectCount implements Comparator<Vehicle> {
    @Override
    public int compare(Vehicle o1, Vehicle o2) {
        return Integer.compare(MechanicService.getDefectCount(o2), MechanicService.getDefectCount(o1));
    }
}
