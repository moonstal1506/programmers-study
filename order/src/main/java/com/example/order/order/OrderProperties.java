package com.example.order.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;


@Configuration
@ConfigurationProperties(prefix = "kdt")// yml kdt 하위 바인딩됨
public class OrderProperties implements InitializingBean {// 프로퍼티 속성 그룹화 시킬 때 주입받아씀

    private static final Logger log = LoggerFactory.getLogger(OrderProperties.class);

    private String version;

    private int minimumOrderAmount;

    private List<String> supportVendors;

    private String description;

    @Value("${JAVA_HOME}")//환경변수값
    private String javaHome;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("version-> {}", version);
        log.debug("minimumOrderAmount->{}", minimumOrderAmount);
        log.debug("supportVendors->{}", supportVendors);
        log.debug("javaHome->{}", javaHome);
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
