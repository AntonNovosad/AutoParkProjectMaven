package by.devincubator.infrastructure.orm.service;

import by.devincubator.infrastructure.core.Context;
import by.devincubator.infrastructure.core.annotations.Autowired;
import by.devincubator.infrastructure.core.annotations.InitMethod;
import by.devincubator.infrastructure.orm.ConnectionFactory;
import by.devincubator.infrastructure.orm.annotations.Column;
import by.devincubator.infrastructure.orm.annotations.ID;
import by.devincubator.infrastructure.orm.annotations.Table;
import by.devincubator.infrastructure.orm.enums.SqlFieldType;
import lombok.Setter;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

@Setter
public class PostgreDataBaseService {
    private static final String SEQ_NAME = "id_seq";
    private static final String CHECK_SEQ_SQL_PATTERN =
            "SELECT EXISTS (\n" +
                    "    SELECT FROM information_schema.sequences \n" +
                    "    WHERE sequence_schema = 'public' \n" +
                    "    AND   sequence_name   = '%s' \n" +
                    ");";
    private static final String CREATE_ID_SEQ_PATTERN =
            "CREATE SEQUENCE %s\n" +
                    "    INCREMENT 1\n" +
                    "    START 1;";
    private static final String CHECK_TABLE_SQL_PATTERN =
            "SELECT EXISTS (\n" +
                    "    SELECT FROM information_schema.tables \n" +
                    "    WHERE table_schema = 'public' \n" +
                    "    AND   table_name   = '%s' \n" +
                    ");";
    private static final String CREATE_TABLE_SQL_PATTERN =
            "CREATE TABLE %s (\n" +
                    "    %s integer PRIMARY KEY DEFAULT nextval('%s')" +
                    "    %s\n);";
    private static final String INSERT_SQL_PATTERN =
            "INSERT INTO %s(%s)\n" +
                    "    VALUES (%s);";

    @Autowired
    private ConnectionFactory connectionFactory;
    private Map<String, String> classToSql;
    private Map<String, String> insertPatternByClass;
    @Autowired
    private Context context;
    private Map<String, String> insertByClassPattern;

    public PostgreDataBaseService() {
    }

    @InitMethod
    public void init() {
        classToSql = Arrays.stream(SqlFieldType.values())
                .collect(Collectors.toMap(sqlFieldType -> sqlFieldType.getType().getName(), SqlFieldType::getSqlType));
        insertPatternByClass = Arrays.stream(SqlFieldType.values())
                .collect(Collectors.toMap(sqlFieldType -> sqlFieldType.getType().getName(), SqlFieldType::getInsertPattern));
        if (!checkIDSeq()) {
            createIDSeq();
        }
        Set<Class<?>> entities = context.getConfig().getScanner().getReflections().getTypesAnnotatedWith(Table.class);
        validateEntities(entities);
        checkTable(entities);
        insertByClassPattern = new HashMap<>();
        entities.stream().forEach(entity -> insertByClassPattern.put(entity.getName(), getInsertQuery(entity)));
    }

    public Long save(Object obj) {
        Long id = null;
        Field idField = getFieldID(obj.getClass().getDeclaredFields());
        String idFieldName = idField.getName();
        Object[] values = getValues(obj);
        String sql = String.format(insertByClassPattern.get(obj.getClass().getName()), values);

        try {
            Connection connection = connectionFactory.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            id = resultSet.getLong(idFieldName);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            idField.setAccessible(true);
            idField.set(obj, id);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return id;
    }

    public <T> Optional<T> get(Long id, Class<T> clazz) {
        checkTableAnnotation(clazz);
        String sql = "SELECT * FROM " + clazz.getAnnotation(Table.class).name()
                + " WHERE " + getFieldID(clazz.getDeclaredFields()).getAnnotation(ID.class).name()
                + " = " + id + ";";

        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            return Optional.of(makeObject(resultSet, clazz));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @SneakyThrows
    public <T> List<T> getAll(Class<T> clazz) {
        List<T> list = new ArrayList<>();
        checkTableAnnotation(clazz);
        String sql = "SELECT * FROM " + clazz.getAnnotation(Table.class).name() + ";";

        try {
            Connection connection = connectionFactory.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                list.add(makeObject(resultSet, clazz));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private boolean checkIDSeq() {
        String sql = String.format(CHECK_SEQ_SQL_PATTERN, SEQ_NAME);
        try {
            Connection connection = connectionFactory.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getBoolean("exists");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void createIDSeq() {
        String sql = String.format(CREATE_ID_SEQ_PATTERN, SEQ_NAME);
        try (Connection connection = connectionFactory.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void validateEntities(Set<Class<?>> entities) {
        for (Class<?> clazz : entities) {
            Field[] fields = clazz.getDeclaredFields();
            if (!checkIDEntities(fields)) {
                throw new IllegalStateException("Class " + entities.getClass() + " doesn't have ID field");
            }
            if (!checkColumnEntities(fields)) {
                throw new IllegalStateException("Class " + entities.getClass() + " doesn't have Column field");
            }
        }
    }

    private boolean checkIDEntities(Field[] fields) {
        for (Field field : fields) {
            if (field.isAnnotationPresent(ID.class)) {
                if (field.getType().equals(Long.class)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkColumnEntities(Field[] fields) {
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                if (field.getType().isPrimitive() && !field.getAnnotation(Column.class).unique()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void checkTable(Set<Class<?>> entities) {
        for (Class<?> entity : entities) {
            String str = entity.getDeclaredAnnotation(Table.class).name();
            if (!isTableExist(str)) {
                createTable(entity);
            }
        }
    }

    private boolean isTableExist(String tableName) {
        String sql = String.format(CHECK_TABLE_SQL_PATTERN, tableName);
        try {
            Connection connection = connectionFactory.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return resultSet.getBoolean("exists");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void createTable(Class<?> type) {
        Field[] fieldArray = type.getDeclaredFields();
        String tableName = type.getAnnotation(Table.class).name();
        String idField = getFieldID(type.getDeclaredFields()).getName();
        String fields = createStringFields(fieldArray);
        String sql = String.format(CREATE_TABLE_SQL_PATTERN, tableName, idField, SEQ_NAME, fields);
        try {
            Connection connection = connectionFactory.getConnection();
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String createStringFields(Field[] fields) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                stringBuilder.append(createString(field.getAnnotation(Column.class), field.getType().getName()));
            }
        }
        return stringBuilder.toString();
    }

    private String createString(Column column, String name) {
        StringBuilder stringBuilder = new StringBuilder();
        String sqlType = classToSql.get(name);
        stringBuilder.append(",\n\t");
        stringBuilder.append(column.name());
        stringBuilder.append(" ").append(sqlType);
        stringBuilder.append(column.nullable() ? "" : " NOT NULL");
        stringBuilder.append(column.unique() ? " UNIQUE" : "");
        return stringBuilder.toString();
    }

    private String getInsertQuery(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        String tableName = clazz.getDeclaredAnnotation(Table.class).name();
        String insertFields = getColumnsName(fields);
        String values = getColumnsValues(fields);
        String idFieldName = getFieldID(fields).getAnnotation(ID.class).name();
        String sql = String.format(INSERT_SQL_PATTERN, tableName, insertFields, values, idFieldName);
        return sql;
    }

    private String getColumnsName(Field[] fields) {
        String str = Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(field -> field.getDeclaredAnnotation(Column.class).name())
                .reduce((name1, name2) -> name1 + ", " + name2).get();
        return str;
    }

    private String getColumnsValues(Field[] fields) {
        return Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(field -> insertPatternByClass.get(field.getType().getName()))
                .reduce((s, s2) -> s + "," + s2)
                .get();
    }

    private Field getFieldID(Field[] fields) {
        for (Field field : fields) {
            if (field.isAnnotationPresent(ID.class)) {
                return field;
            }
        }
        throw new IllegalStateException("Field ID not found");
    }

    private Object[] getValues(Object obj) {
        Object[] values = Arrays.stream(obj.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(field -> {
                            field.setAccessible(true);
                            try {
                                return field.get(obj);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            return new Object();
                        }
                )
                .toArray();
        return values;
    }

    private void checkTableAnnotation(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Table.class)) {
            throw new IllegalStateException("Class" + clazz.getSimpleName() + "doesn't have Table annotation");
        }
    }

    @SneakyThrows
    private <T> T makeObject(ResultSet resultSet, Class<T> clazz) {
        Method method;
        T obj = clazz.getConstructor().newInstance();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(ID.class)) {
                String setterName = createSetterString(field);
                method = obj.getClass().getMethod(setterName, field.getType());
                getMethodForType(method, field, obj, resultSet);
            }
            if (field.isAnnotationPresent(Column.class)) {
                String setterName = createSetterString(field);
                method = obj.getClass().getMethod(setterName, field.getType());
                getMethodForType(method, field, obj, resultSet);
            }
        }
        for (Method m : clazz.getMethods()) {
            if (m.isAnnotationPresent(InitMethod.class)) {
                m.invoke(obj);
            }
        }
        return obj;
    }

    private String createSetterString(Field field) {
        String fieldName = field.getName();
        return "set" + String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1);
    }

    @SneakyThrows
    private void getMethodForType(Method setterMethod, Field field, Object object, ResultSet resultSet) {
        if (field.getType() == String.class) {
            setterMethod.invoke(object, resultSet.getString(field.getName()));
        } else if (field.getType() == Integer.class) {
            setterMethod.invoke(object, resultSet.getInt(field.getName()));
        } else if (field.getType() == Long.class) {
            setterMethod.invoke(object, resultSet.getLong(field.getName()));
        } else if (field.getType() == Double.class) {
            setterMethod.invoke(object, resultSet.getDouble(field.getName()));
        } else if (field.getType() == Date.class) {
            setterMethod.invoke(object, resultSet.getDate(field.getName()));
        }
    }
}
