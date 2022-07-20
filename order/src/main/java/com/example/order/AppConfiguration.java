package com.example.order;

import com.example.order.voucher.MemoryVoucherRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = {"com.example.order.order", "com.example.order.voucher"})
//@ComponentScan(basePackageClasses = {Order.class, Voucher.class})//Order, Voucher 가 속한 패키지 기준
//@ComponentScan(
//        basePackages = {"com.example.order.order", "com.example.order.voucher"},
//        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,value = MemoryVoucherRepository.class)})
public class AppConfiguration {
}