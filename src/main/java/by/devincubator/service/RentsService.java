package by.devincubator.service;

import by.devincubator.entity.Rents;
import by.devincubator.infrastructure.core.annotations.Autowired;
import by.devincubator.infrastructure.core.annotations.InitMethod;
import by.devincubator.infrastructure.orm.EntityManager;
import lombok.Setter;

import java.util.List;

@Setter
public class RentsService {
    @Autowired
    EntityManager entityManager;

    @InitMethod
    public void init() {
    }

    public Rents get(Long id) {
        return entityManager.get(id, Rents.class).get();
    }

    public List<Rents> getAll() {
        return entityManager.getAll(Rents.class);
    }

    public Long save(Rents rents) {
        return entityManager.save(rents);
    }
}
