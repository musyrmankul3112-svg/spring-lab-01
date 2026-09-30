package kz.iitu.springlab.audit;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RetryOnFailure {

    int attempts() default 3;
}