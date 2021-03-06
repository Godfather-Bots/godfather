package dev.struchkov.godfather.context.domain.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({METHOD, PARAMETER})
public @interface Unit {

    String value() default "";

    boolean lazy() default false;

    boolean mainUnit() default false;

}
