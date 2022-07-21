package com.example.order.order;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderProperties implements InitializingBean {

//    @Value("v1.1.1")
//    @Value("${kdt.version}")
    @Value("${kdt.version2:v0.0.0}")//없으면 :v0.0.0
    private String version;

//    @Value("1")
    @Value("${kdt.minimum-order-amount}")
    private int minimumOrderAmount;

//    @Value("a,b,c")
    @Value("${kdt.support-vendors}")
    private List<String> supportVendors;

    @Value("${JAVA_HOME}")//환경변수값
    private String javaHome;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("version = " + version);
        System.out.println("minimumOrderAmount = " + minimumOrderAmount);
        System.out.println("supportVendors = " + supportVendors);
        System.out.println("javaHome = " + javaHome);
    }
}
