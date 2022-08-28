package by.devincubator.service;

import by.devincubator.entity.Types;
import by.devincubator.infrastructure.core.annotations.Autowired;
import by.devincubator.infrastructure.core.annotations.InitMethod;
import by.devincubator.infrastructure.orm.EntityManager;
import lombok.Setter;

import java.util.List;

@Setter
public class TypesService {
    @Autowired
    EntityManager entityManager;

    @InitMethod
    public void init() {
    }

    public Types get(Long id) {
        return entityManager.get(id, Types.class).get();
    }

    public List<Types> getAll() {
        return entityManager.getAll(Types.class);
    }

    public Long save(Types types) {
        return entityManager.save(types);
    }
}
