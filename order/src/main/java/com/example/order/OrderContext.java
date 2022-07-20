package com.example.order;

import java.util.Optional;
import java.util.UUID;

public class OrderContext {

    public VoucherRepository voucherRepository(){
        return new MemoryVoucherRepository();
    }

    public OrderRepository orderRepository(){
        return new MemoryOrderRepository();
    }

    public VoucherService voucherService(){
        return new VoucherService(voucherRepository());
    }

    public OrderService orderService(){
        return new OrderService(voucherService(), orderRepository());
    }
}
