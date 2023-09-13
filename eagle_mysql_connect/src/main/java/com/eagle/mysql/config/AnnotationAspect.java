package com.eagle.mysql.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AnnotationAspect {
    @Around("@annotation(com.eagle.mysql.annotation.Check)")
    public Object aroundABCAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
        // 在注解@ABC的方法执行前执行
        System.out.println("Before @ABC");

        // 执行被切方法
        Object result = joinPoint.proceed();

        // 在注解@ABC的方法执行后执行
        System.out.println("After @ABC");

        return result;
    }
}
