package by.devincubator.infrastructure.core;

public interface Cache {
    boolean contains(Class<?> clazz);
    <T> T get(Class<T> clazz);
    <T> void put(Class<T> clazz, T value);
}
