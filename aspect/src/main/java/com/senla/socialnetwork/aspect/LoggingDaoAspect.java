package com.senla.socialnetwork.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingDaoAspect {
    private static final String LOG_METHOD_STRING = "    [Class: {}][method: {}]";
    private static final String LOG_ARG_STRING = "      [arg: {}]";
    private static final String LOG_RETURN_VALUE_STRING = "      [returned value: {}]";
    private static final String LOG_BENCHMARK = "      [the method \"{}\" worked for {} ms]";

    @Before("annotatedRepository()")
    public void logClassAndMethodCall(final JoinPoint joinPoint) {
        log.debug(LOG_METHOD_STRING, joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName());
        for (Object arg : joinPoint.getArgs()) {
            log.debug(LOG_ARG_STRING, arg.toString());
        }
    }

    @AfterReturning(pointcut = "annotatedRepository()", returning = "result")
    public void logReturningValue(final Object result) {
        if (result != null) {
            log.debug(LOG_RETURN_VALUE_STRING, result.toString());
        }
    }

    @Around("annotatedRepository()")
    public Object logThrow(final ProceedingJoinPoint joinPoint) {
        try {
            long start = System.currentTimeMillis();
            Object proceed = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - start;
            log.info(LOG_BENCHMARK, joinPoint.getSignature().getName(), executionTime);
            return proceed;
        } catch (Throwable throwable) {
            log.error("[{}]", throwable.getMessage());
            return null;
        }
    }

    @Pointcut("@within(org.springframework.stereotype.Repository)")
    private void annotatedRepository() { }

}
