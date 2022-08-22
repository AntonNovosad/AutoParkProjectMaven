package by.devincubator.infrastructure.orm.enums;

import java.util.Date;

public enum SqlFieldType {
    INTEGER(Integer.class, "integer", "%s"),
    LONG(Long.class, "integer", "%s"),
    DOUBLE(Double.class, "decimal", "%s"),
    STRING(String.class, "varchar(255)", "%s"),
    DATE(Date.class, "date", "%s");

    SqlFieldType(Class<?> type, String sqlType, String insertPattern) {
        this.type = type;
        this.sqlType = sqlType;
        this.insertPattern = insertPattern;
    }

    private final Class<?> type;
    private final String sqlType;
    private final String insertPattern;

    public Class<?> getType() {
        return type;
    }

    public String getSqlType() {
        return sqlType;
    }

    public String getInsertPattern() {
        return insertPattern;
    }
}
