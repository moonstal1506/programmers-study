package com.example.order.aop;

import com.example.order.order.Order;
import com.example.order.order.OrderItem;
import com.example.order.order.OrderService;
import com.example.order.order.OrderStatus;
import com.example.order.voucher.FixedAmountVoucher;
import com.example.order.voucher.VoucherRepository;
import com.example.order.voucher.VoucherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@SpringJUnitConfig
@ActiveProfiles("test")
public class AopTest {


    @Configuration
    @ComponentScan(basePackages = {"com.example.order.voucher", "com.example.order.aop"})
    @EnableAspectJAutoProxy //aop 적용
    static class Config{
    }

    @Autowired
    ApplicationContext context;

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    VoucherService voucherService;

    @Test
    @DisplayName("AopTest")
    public void testOrderService() {
        FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        voucherRepository.insert(voucher);
        //Before method called. Voucher com.example.order.voucher.VoucherRepository.insert(Voucher)
        //After method called with result -> FixedAmountVoucher{voucherId=3aca6db3-ab35-4196-91de-538ebc83f7a0, amount=100}

        voucherService.getVoucher(voucher.getVoucherId());
        //Before method called. Voucher com.example.order.voucher.VoucherService.getVoucher(UUID)
        //After method called with result -> FixedAmountVoucher{voucherId=f884588a-e73b-4248-afeb-2a2647563011, amount=100}
    }

    @Test
    @DisplayName("빈으로 등록되지 않은 VoucherService 메서드 호출하면 aop 적용되지 않는다.")
    public void beanX() {
        FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        voucherRepository.insert(voucher);

        //빈으로 등록되지 않은 VoucherService
        VoucherService voucherService = new VoucherService(voucherRepository);
        voucherService.getVoucher(voucher.getVoucherId());
    }
}