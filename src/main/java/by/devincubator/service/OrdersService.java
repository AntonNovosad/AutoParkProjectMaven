package by.devincubator.service;

import by.devincubator.entity.Orders;
import by.devincubator.infrastructure.core.annotations.Autowired;
import by.devincubator.infrastructure.core.annotations.InitMethod;
import by.devincubator.infrastructure.orm.EntityManager;
import lombok.Setter;

import java.util.List;

@Setter
public class OrdersService {
    @Autowired
    EntityManager entityManager;

    @InitMethod
    public void init() {
    }

    public Orders get(Long id) {
        return entityManager.get(id, Orders.class).get();
    }

    public List<Orders> getAll() {
        return entityManager.getAll(Orders.class);
    }

    public Long save(Orders orders) {
        return entityManager.save(orders);
    }
}
