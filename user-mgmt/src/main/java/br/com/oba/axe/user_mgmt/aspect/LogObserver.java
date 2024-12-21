package br.com.oba.axe.user_mgmt.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LogObserver {
    @Around("@annotation(br.com.oba.axe.user_mgmt.aspect.annotation.Logifier)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object[] params = joinPoint.getArgs();

        String className = joinPoint.getSignature().getName();
        String methodName = joinPoint.getSignature().getName();

        log.info("Starting execution method {}.{}", className, methodName);

        for(Object param : params) {
            log.info("Method param {}", param);
        }

        Object result = joinPoint.proceed();

        log.info("Finished execution method {}.{}", className, methodName);

        long duration = System.currentTimeMillis() - start;

        log.info("The execution has completed around {} ms", duration);

        log.info("Returning {}", result);

        return result;
    }
}
