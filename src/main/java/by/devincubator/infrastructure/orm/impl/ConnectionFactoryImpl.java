package by.devincubator.infrastructure.orm.impl;

import by.devincubator.infrastructure.core.annotations.InitMethod;
import by.devincubator.infrastructure.core.annotations.Property;
import by.devincubator.infrastructure.orm.ConnectionFactory;
import lombok.Setter;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

@Setter
public class ConnectionFactoryImpl implements ConnectionFactory {
    @Property("url")
    private String url;
    @Property("username")
    private String username;
    @Property("password")
    private String password;
    private Connection connection;

    public ConnectionFactoryImpl() {
    }

    @SneakyThrows
    @InitMethod
    public void initConnection() {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(url, username, password);
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}
