package com.example.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class CommonPointcut {

    @Pointcut("execution(public * com.example.order..*Service.*(..))")
    public void servicePublicMethodPointcut() {};

    @Pointcut("execution(* com.example.order..*Repository.*(..))")
    public void repositoryMethodPointcut() {};

    @Pointcut("execution(public * com.example.order..*Repository.insert(..))")
    public void repositoryInsertMethodPointcut() {};
}
