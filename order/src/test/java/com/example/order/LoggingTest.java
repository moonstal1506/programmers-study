package com.example.order;

import com.example.order.order.OrderProperties;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LoggingTest {

    private static final Logger log = LoggerFactory.getLogger(LoggingTest.class);

    @Test
    void logging() throws Exception {
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(AppConfiguration.class);

        OrderProperties orderProperties = applicationContext.getBean(OrderProperties.class);

        log.info("logger name=> {}", log.getName());
        log.info("Version->{}", orderProperties.getVersion());
        log.info("MinimumOrderAmount->{}", orderProperties.getMinimumOrderAmount());
        log.info("supportVendors->{}", orderProperties.getSupportVendors());
        log.info("description->{}", orderProperties.getDescription());
    }
}
