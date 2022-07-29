package com.example.order.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect //Aspect는 여러 어드바이스(메서드)를 담고있다
@Component //빈으로 등록 되어야함
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("@annotation(com.example.order.aop.TrackTime)")//TrackTime annotation 붙은 메서드에만 적용
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Before method called. {}", joinPoint.getSignature().toString());
        long startTime = System.nanoTime();
        Object result = joinPoint.proceed();
        long endTime = System.nanoTime() - startTime;
        log.info("After method called with result -> {} and time taken {} nanoseconds", result, endTime);
        return result;
    }


}
