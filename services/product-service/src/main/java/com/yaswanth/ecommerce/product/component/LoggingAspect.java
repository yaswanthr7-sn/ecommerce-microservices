package com.yaswanth.ecommerce.product.component;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("execution(* com.yaswanth.ecommerce.product.service..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.nanoTime();

        try {
            return joinPoint.proceed();
        } finally {
            long elapsed = System.nanoTime() - start;

            log.info(
                    "Method {} executed in {} ms",
                    joinPoint.getSignature().toShortString(),
                    elapsed / 1_000_000.0
            );
        }
    }
}
