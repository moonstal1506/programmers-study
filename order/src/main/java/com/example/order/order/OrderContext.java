package com.example.order.order;

import com.example.order.voucher.MemoryOrderRepository;
import com.example.order.voucher.MemoryVoucherRepository;
import com.example.order.voucher.VoucherRepository;
import com.example.order.voucher.VoucherService;

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
