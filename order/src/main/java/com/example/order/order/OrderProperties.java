package com.example.order.order;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;


@Configuration
@ConfigurationProperties(prefix = "kdt")// yml kdt 하위 바인딩됨
public class OrderProperties implements InitializingBean {// 프로퍼티 속성 그룹화 시킬 때 주입받아씀

    //    @Value("v1.1.1")
//    @Value("${kdt.version}")
//    @Value("${kdt.version2:v0.0.0}")//없으면 :v0.0.0
    private String version;

    //    @Value("1")
//    @Value("${kdt.minimum-order-amount}")
    private int minimumOrderAmount;

    //    @Value("a,b,c")
//    @Value("${kdt.support-vendors}")
    private List<String> supportVendors;

    private String description;

    @Value("${JAVA_HOME}")//환경변수값
    private String javaHome;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("version = " + version);
        System.out.println("minimumOrderAmount = " + minimumOrderAmount);
        System.out.println("supportVendors = " + supportVendors);
        System.out.println("javaHome = " + javaHome);
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getMinimumOrderAmount() {
        return minimumOrderAmount;
    }

    public void setMinimumOrderAmount(int minimumOrderAmount) {
        this.minimumOrderAmount = minimumOrderAmount;
    }

    public List<String> getSupportVendors() {
        return supportVendors;
    }

    public void setSupportVendors(List<String> supportVendors) {
        this.supportVendors = supportVendors;
    }

    public String getJavaHome() {
        return javaHome;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
