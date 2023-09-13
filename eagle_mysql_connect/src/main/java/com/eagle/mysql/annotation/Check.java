package com.eagle.mysql.annotation;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Check {
    String value() default ""; // 添加一个属性，可以根据需要进行定义
    int priority() default 0; // 添加另一个属性，可以根据需要进行定义
}