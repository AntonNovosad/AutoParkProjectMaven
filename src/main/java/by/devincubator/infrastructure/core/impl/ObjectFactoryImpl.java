package by.devincubator.infrastructure.core.impl;

import by.devincubator.infrastructure.configurators.ObjectConfigurator;
import by.devincubator.infrastructure.configurators.ProxyConfigurator;
import by.devincubator.infrastructure.core.Context;
import by.devincubator.infrastructure.core.ObjectFactory;
import by.devincubator.infrastructure.core.Scanner;
import by.devincubator.infrastructure.core.annotations.InitMethod;
import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ObjectFactoryImpl implements ObjectFactory {
    private final List<ProxyConfigurator> proxyConfigurators = new ArrayList<>();
    private final Context context;
    private final List<ObjectConfigurator> objectConfigurators = new ArrayList<>();

    @SneakyThrows
    public ObjectFactoryImpl(Context context) {
        this.context = context;
        Scanner scanner = context.getConfig().getScanner();
        Set set = scanner.getSubTypeOf(ObjectConfigurator.class);
        Set<Class<? extends ProxyConfigurator>> proxySet = scanner.getSubTypeOf(ProxyConfigurator.class);
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Class<? extends ObjectConfigurator> subClass = (Class<? extends ObjectConfigurator>) iterator.next();
            objectConfigurators.add(subClass.getConstructor().newInstance());
        }
        for (Class<? extends ProxyConfigurator> proxyConfiguratorClass : proxySet) {
            Constructor<? extends ProxyConfigurator> constructor = proxyConfiguratorClass.getConstructor();
            proxyConfigurators.add(constructor.newInstance());
        }
    }

    @SneakyThrows
    @Override
    public <T> T createObject(Class<T> implementation) {
        T object = create(implementation);
        configure(object);
        initialize(implementation, object);
        object = makeProxy(implementation, object);
        return object;
    }

    private <T> T makeProxy(Class<T> implClass, T object) {
        for (ProxyConfigurator proxyConfigurator : proxyConfigurators) {
            object = proxyConfigurator.makeProxy(object, implClass, context);
        }
        return object;
    }

    private <T> T create(Class<T> implementation) throws Exception {
        return implementation.getConstructor().newInstance();
    }

    private <T> void configure(T object) {
        objectConfigurators.forEach(objectConfigurator -> objectConfigurator.configure(object, context));
    }

    private <T> void initialize(Class<T> implementation, T object) throws Exception {
        for (Method method : implementation.getMethods()) {
            if (method.isAnnotationPresent(InitMethod.class)) {
                method.invoke(object);
            }
        }
    }
}
