package com.linus.commons;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {
    public String tableAlias() default "";
    public String tableField() default "";
}
