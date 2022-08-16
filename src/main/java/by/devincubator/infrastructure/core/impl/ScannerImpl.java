package by.devincubator.infrastructure.core.impl;

import by.devincubator.infrastructure.core.Scanner;
import org.reflections.Reflections;

import java.util.Set;

public class ScannerImpl implements Scanner {
    private Reflections reflections;

    public ScannerImpl(String packageName) {
        reflections = new Reflections(packageName);
    }

    @Override
    public <T> Set<Class<? extends T>> getSubTypeOf(Class<T> type) {
        return reflections.getSubTypesOf(type);
    }

    @Override
    public Reflections getReflections() {
        return reflections;
    }
}
