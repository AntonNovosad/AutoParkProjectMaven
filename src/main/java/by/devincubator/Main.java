package by.devincubator;

import by.devincubator.infrastructure.core.impl.ApplicationContext;
import by.devincubator.service.BadMechanicService;
import by.devincubator.service.Fixer;
import by.devincubator.service.Workroom;
import by.devincubator.vehicle.Vehicle;
import by.devincubator.vehicle.collection.VehicleCollection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<Class<?>, Class<?>> interfaceToImplementation = new HashMap<>();
        interfaceToImplementation.put(Fixer.class, BadMechanicService.class);

        ApplicationContext applicationContext = new ApplicationContext("by", interfaceToImplementation);
        VehicleCollection vehicleCollection = applicationContext.getObject(VehicleCollection.class);
        Workroom workroom = applicationContext.getObject(Workroom.class);

        List<Vehicle> vehicleList = vehicleCollection.getVehicleList();
        workroom.checkAllVehicle(vehicleList);
    }
}