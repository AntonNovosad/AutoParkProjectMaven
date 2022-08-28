package by.devincubator.infrastructure.configurators;

import by.devincubator.infrastructure.core.Context;

public interface ProxyConfigurator {
    <T> T makeProxy(T object, Class<T> implementation, Context context);
}
