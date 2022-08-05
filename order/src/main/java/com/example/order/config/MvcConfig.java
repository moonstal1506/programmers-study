package com.example.order.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedMethods("GET", "POST")
                .allowedOrigins("*");
    }

    @Override //configureMessageConverters 모든 메시지 컨버터를 오버라이드 해버림 -> extendMessageConverters 확장
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //xml
        MarshallingHttpMessageConverter messageConverter = new MarshallingHttpMessageConverter();
        XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
        messageConverter.setMarshaller(xStreamMarshaller);
        messageConverter.setUnmarshaller(xStreamMarshaller);//xml을 자바 인스턴스로
        converters.add(0,messageConverter); //순서줌
    }
}
