package by.devincubator.vehicle.comparator;

import by.devincubator.vehicle.Vehicle;

import java.util.Comparator;

public class MyComparator implements Comparator<Vehicle> {
    @Override
    public int compare(Vehicle o1, Vehicle o2) {
        return o1.getModelName().compareTo(o2.getModelName());
    }
}
