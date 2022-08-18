package by.devincubator.infrastructure.core.impl;

import by.devincubator.infrastructure.core.Cache;

import java.util.HashMap;
import java.util.Map;

public class CacheImpl implements Cache {
    private Map<String, Object> cache;

    public CacheImpl() {
        cache = new HashMap<>();
    }

    @Override
    public boolean contains(Class<?> clazz) {
        return cache.containsKey(clazz.getSimpleName());
    }

    @Override
    public <T> T get(Class<T> clazz) {
        return (T) cache.get(clazz.getSimpleName());
    }

    @Override
    public <T> void put(Class<T> clazz, T value) {
        cache.put(clazz.getSimpleName(), value);
    }
}
