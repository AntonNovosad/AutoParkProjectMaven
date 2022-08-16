package by.devincubator.infrastructure.core;

import org.reflections.Reflections;

import java.util.Set;

public interface Scanner {
    <T> Set<Class<? extends T>> getSubTypeOf(Class<T> type);
    Reflections getReflections();
}
