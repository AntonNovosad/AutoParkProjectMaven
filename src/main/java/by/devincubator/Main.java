package by.devincubator;

import by.devincubator.entity.Vehicles;
import by.devincubator.infrastructure.core.impl.ApplicationContext;
import by.devincubator.parser.ParserVehicleInterface;
import by.devincubator.parser.impl.ParserVehicleFromDB;
import by.devincubator.vehicle.collection.VehicleCollection;
import by.devincubator.vehicle.service.Fixer;
import by.devincubator.vehicle.service.MechanicService;
import by.devincubator.vehicle.service.Workroom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<Class<?>, Class<?>> interfaceToImplementation = new HashMap<>();
        interfaceToImplementation.put(Fixer.class, MechanicService.class);

        ApplicationContext applicationContext = new ApplicationContext("by", interfaceToImplementation);
        VehicleCollection vehicleCollection = applicationContext.getObject(VehicleCollection.class);
        Workroom workroom = applicationContext.getObject(Workroom.class);

        vehicleCollection.getVehicleList().forEach(vehicles -> System.out.println(vehicles.getRentList()));

        List<Vehicles> vehicleList = vehicleCollection.getVehicleList();
        workroom.checkAllVehicle(vehicleList);
    }
}