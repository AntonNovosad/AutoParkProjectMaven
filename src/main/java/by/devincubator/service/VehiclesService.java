package by.devincubator.service;

import by.devincubator.entity.Vehicles;
import by.devincubator.infrastructure.core.annotations.Autowired;
import by.devincubator.infrastructure.core.annotations.InitMethod;
import by.devincubator.infrastructure.orm.EntityManager;
import lombok.Setter;

import java.util.List;

@Setter
public class VehiclesService {
    @Autowired
    EntityManager entityManager;

    @InitMethod
    public void init() {
    }

    public Vehicles get(Long id) {
        return entityManager.get(id, Vehicles.class).get();
    }

    public List<Vehicles> getAll() {
        return entityManager.getAll(Vehicles.class);
    }

    public Long save(Vehicles vehicles) {
        return entityManager.save(vehicles);
    }
}
