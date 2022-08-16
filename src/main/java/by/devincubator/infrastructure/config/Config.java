package by.devincubator.infrastructure.config;

import by.devincubator.infrastructure.core.Scanner;

public interface Config {
    <T> Class<? extends T> getImplementation(Class<T> target);
    Scanner getScanner();
}
