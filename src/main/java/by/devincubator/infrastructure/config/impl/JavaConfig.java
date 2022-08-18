package by.devincubator.infrastructure.config.impl;

import by.devincubator.infrastructure.config.Config;
import by.devincubator.infrastructure.core.Scanner;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.Set;

@AllArgsConstructor
public class JavaConfig implements Config {
    private final Scanner scanner;
    private final Map<Class<?>, Class<?>> interfaceToImplementation;

    @Override
    public <T> Class<? extends T> getImplementation(Class<T> target) {
        Set<Class<? extends T>> list = scanner.getSubTypeOf(target);
        if (list.size() != 1) {
            if (interfaceToImplementation.containsKey(target)) {
                return (Class<? extends T>) interfaceToImplementation.get(target);
            }
            throw new RuntimeException("target interface has 0 or more than one impl");
        } else {
            return list.iterator().next();
        }
    }

    @Override
    public Scanner getScanner() {
        return scanner;
    }
}
