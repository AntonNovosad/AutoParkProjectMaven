package by.devincubator;

import by.devincubator.entity.Vehicles;
import by.devincubator.infrastructure.core.Context;
import by.devincubator.infrastructure.orm.EntityManager;
import by.devincubator.infrastructure.threads.annotations.Schedule;
import by.devincubator.vehicle.service.Workroom;

import java.util.List;

public class CheckThread {
    public CheckThread() {
    }

    @Schedule(delta = 10000, timeout = 10000)
    public void getVehicleFromDBToWorkroom(Context context) {
        EntityManager entityManager = context.getObject(EntityManager.class);
        List<Vehicles> vehicles = entityManager.getAll(Vehicles.class);
        context.getObject(Workroom.class).checkAllVehicle(vehicles);
    }
}
