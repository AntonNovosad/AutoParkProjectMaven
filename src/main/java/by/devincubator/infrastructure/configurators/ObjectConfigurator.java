package by.devincubator.infrastructure.configurators;

import by.devincubator.infrastructure.core.Context;
import by.devincubator.infrastructure.core.annotations.Autowired;
import by.devincubator.infrastructure.core.annotations.Property;

@Autowired
@Property
public interface ObjectConfigurator {
    void configure(Object object, Context context);
}
