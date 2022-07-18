package com.example.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void order() throws Exception {
        UUID customerId = UUID.randomUUID();
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem(UUID.randomUUID(), 100L, 1));
        FixedAmountVoucher fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);
        Order order = new Order(UUID.randomUUID(), customerId, orderItems, fixedAmountVoucher);

        Assert.isTrue(order.totalAmount()==90L,
                MessageFormat.format("totalAmount {0} is not 90L",order.totalAmount()));
        Assertions.assertThat(order.totalAmount()).isEqualTo(90L);
    }


}