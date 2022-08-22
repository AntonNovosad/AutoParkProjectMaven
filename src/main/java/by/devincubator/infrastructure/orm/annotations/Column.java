package by.devincubator.infrastructure.orm.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    String name();
    boolean nullable() default true;
    boolean unique() default false;
}
