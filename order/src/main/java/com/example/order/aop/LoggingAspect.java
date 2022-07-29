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

//    @Pointcut("execution(public * com.example.order..*Service.*(..))")
//    public void servicePublicMethodPointcut() {};

    //포인트 컷: execution(접근제한, 리턴타입, 패키지명.메서드이름()
    //spring aop public만 가능-인터페이스 기반
    //@Around("execution(public * com.example.order..*Service.*(..))")
    //@Around("servicePublicMethodPointcut()")
    @Around("com.example.order.aop.CommonPointcut.servicePublicMethodPointcut()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        //Before method called. Voucher com.example.order.voucher.VoucherRepository.insert(Voucher)
        log.info("Before method called. {}", joinPoint.getSignature().toString());

        Object result = joinPoint.proceed();

        //After method called with result -> FixedAmountVoucher{voucherId=3aca6db3-ab35-4196-91de-538ebc83f7a0, amount=100}
        log.info("After method called with result -> {}", result);
        return result;
    }


}
