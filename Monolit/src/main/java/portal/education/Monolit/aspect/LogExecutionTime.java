package portal.education.Monolit.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
Пишет время работы функции
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogExecutionTime {
    // ms
    long writeErrorIfTimeLess() default 3000L;

    long value() default 0L;

    // log info if writeErrorIfTimeLess the time
    boolean logAnyway() default true;
}




