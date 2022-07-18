package com.example.order;

import java.util.List;
import java.util.UUID;

public class Order {
    private final UUID orderId;
    private final UUID customerId;
    private final List<OrderItem> orderItems;
    private FixedAmountVoucher fixedAmountVoucher;
    private OrderStatus orderStatus = OrderStatus.ACCEPTED;

    public Order(UUID orderId, UUID customerId, List<OrderItem> orderItems, long discountAmount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.fixedAmountVoucher = new FixedAmountVoucher(discountAmount);
    }

    public long totalAmount(){
        //자바14 v.productPrice() * v.quantity()
        Long beforeDiscount = orderItems.stream().map(v -> v.getProductPrice() * v.getQuantity())
                .reduce(0L, Long::sum);
        return fixedAmountVoucher.discount(beforeDiscount); //계산 위임하기
    }


    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
