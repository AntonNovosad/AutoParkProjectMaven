package by.devincubator.infrastructure.configurators.impl;

import by.devincubator.infrastructure.configurators.ObjectConfigurator;
import by.devincubator.infrastructure.core.Context;
import by.devincubator.infrastructure.core.annotations.Autowired;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AutowiredObjectConfigurator implements ObjectConfigurator {
    @Override
    @SneakyThrows
    public void configure(Object object, Context context) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Autowired.class)) {
                String nameSetMethod = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                Method setterMethod = object.getClass().getMethod(nameSetMethod, field.getType());
                setterMethod.invoke(object, context.getObject(field.getType()));
            }
        }
    }
}
