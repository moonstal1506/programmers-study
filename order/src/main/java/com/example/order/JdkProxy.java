package com.example.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class CalculatorImpl implements  Calculator{

    @Override
    public int add(int a, int b) {
        return a+b;
    }
}

interface Calculator{
    int add(int a ,int b);
}

class LoggingInvocationHandler implements InvocationHandler {

    private static final Logger log = LoggerFactory.getLogger(LoggingInvocationHandler.class);
    private final Object targer;

    LoggingInvocationHandler(Object targer) {
        this.targer = targer;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //log 찍는 것을 앞에서 해주고 targer의 메소드를 실행시킴
        //add excuted in com.example.order.CalculatorImpl
        log.info("{} excuted in {}", method.getName(), targer.getClass().getCanonicalName());
        return method.invoke(targer, args);
    }
}

public class JdkProxy {

    private static final Logger log = LoggerFactory.getLogger(JdkProxy.class);

    public static void main(String[] args) {
        //핸들러에는 실제 타겟이 될 객체 필요
        CalculatorImpl calculator = new CalculatorImpl();

        //Aop 구현 클래스와 적용할 인터페이스, 핸들러 전달
        //프록시 만들어짐
        Calculator proxyInstance = (Calculator) Proxy.newProxyInstance(
                LoggingInvocationHandler.class.getClassLoader(),
                new Class[]{Calculator.class},
                new LoggingInvocationHandler(calculator));

        //Handler의 invoke 실행-> target 객체의 add() 실행
        int result = proxyInstance.add(1, 2);
        log.info("add->{}",result);//add->3
    }

}
