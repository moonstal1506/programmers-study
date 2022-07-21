package com.example.order;

import com.example.order.order.OrderProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.text.MessageFormat;

@SpringBootApplication
public class OrderApplication {

    public static void main(String[] args) {
//      SpringApplication.run(OrderApplication.class, args);

        SpringApplication springApplication = new SpringApplication(OrderApplication.class);
        //설정에서 active 가능, arguments --spring.profiles.active=dev
        //아무것도 설정안하면 default
        springApplication.setAdditionalProfiles("local");
        ConfigurableApplicationContext applicationContext = springApplication.run(args);
        OrderProperties orderProperties = applicationContext.getBean(OrderProperties.class);

        System.out.println(MessageFormat.format("getVersion->{0}",orderProperties.getVersion()));
        System.out.println(MessageFormat.format("getMinimumOrderAmount->{0}",orderProperties.getMinimumOrderAmount()));
        System.out.println(MessageFormat.format("supportVendors->{0}",orderProperties.getSupportVendors()));
        System.out.println(MessageFormat.format("description->{0}",orderProperties.getDescription()));
    }
}
