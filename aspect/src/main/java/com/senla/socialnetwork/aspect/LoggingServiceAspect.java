package com.senla.socialnetwork.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingServiceAspect {
    private static final String LOG_METHOD_STRING = "[Class: {}][method: {}]";
    private static final String LOG_ARG_STRING = "  [arg: {}]";
    private static final String LOG_RETURN_VALUE_STRING = "  [Returned value: {}]";

    @Before("annotatedService()")
    public void logClassAndMethodCall(final JoinPoint joinPoint) {
        log.debug(LOG_METHOD_STRING, joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName());
        for (Object arg : joinPoint.getArgs()) {
            log.debug(LOG_ARG_STRING, arg.toString());
        }
    }

    @AfterReturning(pointcut = "annotatedService()", returning = "result")
    public void logReturningValue(final Object result) {
        if (result != null) {
            log.debug(LOG_RETURN_VALUE_STRING, result.toString());
        }
    }

    @AfterThrowing(pointcut = "annotatedService()", throwing = "exception")
    public void logThrow(final Exception exception) {
            log.error("[{}]", exception.getMessage());
    }

    @Pointcut("@within(ServiceLog)")
    private void annotatedService() { }

}
