package com.example.order.order;

import java.util.UUID;


//자바14 레코드 불변객체
//public record OrderItem(UUID productId,long productPrice,long quantity){}

public class OrderItem {
    private final UUID productId;
    private final long productPrice;
    private final long quantity;

    public OrderItem(UUID productId, long productPrice, int quantity) {
        this.productId = productId;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public long getProductPrice() {
        return productPrice;
    }

    public long getQuantity() {
        return quantity;
    }
}
