package com.elice.team4.singleShop.global.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.hibernate.mapping.Join;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class CategoryLoggingAspect {

    @Pointcut("execution(* com.elice.team4.singleShop.category.controller.CategoryController.*(..))")
    public void controllerMethods(){ }

    @Before("controllerMethods()")
    public void controllerLogBefore(JoinPoint joinPoint) {
        log.info("[Before]: {}", joinPoint.getSignature().getName());
    }

    @After("controllerMethods()")
    public void controllerLogAfter(JoinPoint joinPoint) {

        log.info("[After]: {}", joinPoint.getSignature().getName());
    }



    @Pointcut("execution(* com.elice.team4.singleShop.category.service.CategoryService.*(..))")
    public void serviceMethods(){ }

    @Before("serviceMethods()")
    public void serviceLogBefore(JoinPoint joinPoint) {
        log.info("[Before]: {}", joinPoint.getSignature().getName());
    }

    @After("serviceMethods()")
    public void serviceLogAfter(JoinPoint joinPoint) {

        log.info("[After]: {}", joinPoint.getSignature().getName());
    }

    @AfterThrowing(pointcut = "execution(* com.elice.team4.singleShop.category.service.CategoryService.*(..))",
                    throwing = "exception")
    public void serviceLogException(JoinPoint joinPoint, Exception exception) {
        log.error("Exception occurred in {}(): {}", joinPoint.getSignature().getName(), exception.getMessage());
    }
}
