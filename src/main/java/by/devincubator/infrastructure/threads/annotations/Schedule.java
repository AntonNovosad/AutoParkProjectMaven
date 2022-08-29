package by.devincubator.infrastructure.threads.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Schedule {
    int delta();
    int timeout() default Integer.MAX_VALUE;
}
