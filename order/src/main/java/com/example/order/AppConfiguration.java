package com.example.order;

import com.example.order.configuration.YamlPropertiesFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = {"com.example.order.order", "com.example.order.voucher"})
@PropertySource(value = "application.yaml", factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties
public class AppConfiguration {

//    @Bean(initMethod = "init")
//    public BeanOne beanOne() {
//        return new BeanOne();
//    }

}

class BeanOne implements InitializingBean {

    public void init(){
        System.out.println("=============BeanOne.init=============");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("=============BeanOne.afterPropertiesSet=============");
    }
}