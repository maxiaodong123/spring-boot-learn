package com.mxd.mongodb.core.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@ConditionsAnnotation
public @interface Regex {
    // 字段的名称
    String value() default "";
}
