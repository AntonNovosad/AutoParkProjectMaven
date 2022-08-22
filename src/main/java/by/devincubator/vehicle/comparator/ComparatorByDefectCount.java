package by.devincubator.vehicle.comparator;

import by.devincubator.entity.Vehicles;
import by.devincubator.vehicle.service.MechanicService;

import java.util.Comparator;

public class ComparatorByDefectCount implements Comparator<Vehicles> {
    @Override
    public int compare(Vehicles o1, Vehicles o2) {
        return Integer.compare(MechanicService.getDefectCount(o2), MechanicService.getDefectCount(o1));
    }
}
