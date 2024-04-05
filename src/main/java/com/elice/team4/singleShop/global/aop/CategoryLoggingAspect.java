package com.elice.team4.singleShop.global.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class CategoryLoggingAspect {

    @Pointcut("execution(* com.elice.team4.singleShop.category.controller.CategoryController.*(..))")
    public void controllerMethod(){ }

    @Before("controllerMethod()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("[Before]: {}", joinPoint.getSignature().getName());
    }

    @After("controllerMethod()")
    public void logAfter(JoinPoint joinPoint) {

        log.info("[After]: {}", joinPoint.getSignature().getName());
    }
}
