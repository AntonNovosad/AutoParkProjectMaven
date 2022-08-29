package by.devincubator;

import by.devincubator.infrastructure.core.impl.ApplicationContext;
import by.devincubator.vehicle.collection.VehicleCollection;
import by.devincubator.vehicle.service.Fixer;
import by.devincubator.vehicle.service.MechanicService;
import by.devincubator.vehicle.service.Workroom;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<Class<?>, Class<?>> interfaceToImplementation = new HashMap<>();
        interfaceToImplementation.put(Fixer.class, MechanicService.class);

        ApplicationContext applicationContext = new ApplicationContext("by", interfaceToImplementation);
        VehicleCollection vehicleCollection = applicationContext.getObject(VehicleCollection.class);
        Workroom workroom = applicationContext.getObject(Workroom.class);

        CheckThread checkThread = applicationContext.getObject(CheckThread.class);
        checkThread.getVehicleFromDBToWorkroom(applicationContext);

        try {
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}