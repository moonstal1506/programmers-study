package com.example.order;

import com.example.order.order.OrderProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.text.MessageFormat;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.order.order", "com.example.order.voucher"})
public class OrderApplication {

    private static final Logger log = LoggerFactory.getLogger(OrderApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(OrderApplication.class, args);

        OrderProperties orderProperties = applicationContext.getBean(OrderProperties.class);

        log.error("logger name=> {}", log.getName());
        log.warn("Version->{}", orderProperties.getVersion());
        log.warn("MinimumOrderAmount->{}", orderProperties.getMinimumOrderAmount());
        log.info("supportVendors->{}", orderProperties.getSupportVendors());
        log.info("description->{}", orderProperties.getDescription());
    }
}
