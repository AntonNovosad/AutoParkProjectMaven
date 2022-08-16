package by.devincubator.infrastructure.configurators.impl;

import by.devincubator.infrastructure.configurators.ObjectConfigurator;
import by.devincubator.infrastructure.core.Context;
import by.devincubator.infrastructure.core.annotations.Property;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class PropertyObjectConfigurator implements ObjectConfigurator {
    private final Map<String, String> properties;

    @SneakyThrows
    public PropertyObjectConfigurator() {
        URL path = this.getClass().getClassLoader().getResource("application.properties");
        if (path == null) {
            throw new FileNotFoundException(String.format("File '%s' not found", "application.properties"));
        }
        Stream<String> lines = new BufferedReader(
                new InputStreamReader(path.openStream())).lines();
        properties = lines.map(line -> line.split("=")).collect(toMap(arr -> arr[0], arr -> arr[1]));
    }

    @Override
    @SneakyThrows
    public void configure(Object object, Context context) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Property.class)) {
                String nameSetMethod = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                Method setterMethod = object.getClass().getMethod(nameSetMethod, field.getType());
                String value = field.getAnnotation(Property.class).value();
                if (properties.containsKey(value)) {
                    setterMethod.invoke(object, properties.get(value));
                } else {
                    setterMethod.invoke(object, properties.get(field.getName()));
                }
            }
        }
    }
}
