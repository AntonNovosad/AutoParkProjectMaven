package by.devincubator.infrastructure.core;

import by.devincubator.infrastructure.config.Config;

public interface Context {
    <T> T getObject(Class<T> type);
    Config getConfig();
}
