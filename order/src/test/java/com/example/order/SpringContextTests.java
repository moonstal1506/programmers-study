package com.example.order;

import com.example.order.order.*;
import com.example.order.voucher.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration
@SpringJUnitConfig
@ActiveProfiles("test")
public class SpringContextTests {

    @Configuration
    @ComponentScan(basePackages = {"com.example.order.order", "com.example.order.voucher"})
    static class Config{
    }

    @Autowired
    ApplicationContext context;

    @Autowired
    OrderService orderService;

    @Autowired
    VoucherRepository voucherRepository;

    @Test
    @DisplayName("ApplicationContext가 생성되어야 한다.")
    void testApplicationContext() {
        assertThat(context,notNullValue());
    }

    @Test
    @DisplayName("VoucherRepository가 빈으로 등록되어 있어야 한다.")
    void testVoucherRepositoryCreation() {
        VoucherRepository bean = context.getBean(VoucherRepository.class);
        assertThat(bean,notNullValue());
    }

    @Test
    @DisplayName("OrderService를 사용해서 주문을 생성할 수 있다.")
    void testOrderService() {
        //given
        FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        voucherRepository.insert(voucher);

        //when
        Order order = orderService.createOrder(
                UUID.randomUUID(),
                List.of(new OrderItem(UUID.randomUUID(), 200, 1)),
                voucher.getVoucherId());

        //then
        assertThat(order.totalAmount(), is(100L));
        assertThat(order.getVoucher().isEmpty(), is(false));
        assertThat(order.getVoucher().get().getVoucherId(), is(voucher.getVoucherId()));
        assertThat(order.getOrderStatus(), is(OrderStatus.ACCEPTED));
    }
}
