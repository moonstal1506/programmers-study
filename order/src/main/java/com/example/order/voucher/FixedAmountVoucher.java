package com.example.order.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher{
    private static final int MAX_VOUCHER_AMOUNT = 10000;
    private final UUID voucherId;
    private final long amount;

    public FixedAmountVoucher(UUID voucherId, long amount) {
        if(amount<0) {
            throw new IllegalArgumentException("Amount should be positive");
        }
        if(amount==0) {
            throw new IllegalArgumentException("Amount should not be zero");
        }
        if(amount> MAX_VOUCHER_AMOUNT){
            throw new IllegalArgumentException(String.format("Amount should be less than %d", MAX_VOUCHER_AMOUNT));
        }
        this.voucherId = voucherId;
        this.amount = amount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    public long discount(long beforeDiscount) {
        long discountedAmount = beforeDiscount - amount;
        return (discountedAmount < 0) ? 0: discountedAmount;
    }
}
