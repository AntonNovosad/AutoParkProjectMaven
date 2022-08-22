package by.devincubator.infrastructure.orm.impl;

import by.devincubator.infrastructure.core.Context;
import by.devincubator.infrastructure.core.annotations.Autowired;
import by.devincubator.infrastructure.orm.ConnectionFactory;
import by.devincubator.infrastructure.orm.EntityManager;
import by.devincubator.infrastructure.orm.service.PostgreDataBaseService;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Setter
public class EntityManagerImpl implements EntityManager {
    @Autowired
    private ConnectionFactory connection;
    @Autowired
    private PostgreDataBaseService dataBaseService;
    @Autowired
    private Context context;

    public EntityManagerImpl() {
    }

    @Override
    public <T> Optional<T> get(Long id, Class<T> clazz) {
        return dataBaseService.get(id, clazz);
    }

    @Override
    public Long save(Object object) {
        return dataBaseService.save(object);
    }

    @Override
    public <T> List<T> getAll(Class<T> clazz) {
        return dataBaseService.getAll(clazz);
    }
}
