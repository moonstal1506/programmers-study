package com.example.order.voucher;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FixedAmountVoucherTest {

    private static final Logger log = LoggerFactory.getLogger(FixedAmountVoucherTest.class);

    @BeforeAll
    static void setup() {
        log.info("@BeforeAll - 단 한번 실행");
    }

    @BeforeEach
    void init() {
        log.info("@BeforeEach - 매 테스트마다 실행");
    }

    @Test
    @DisplayName("주어진 금액만큼 할인을 해야한다.")
    void testDiscount() {
        FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        assertEquals(900, voucher.discount(1000));
    }

    @Test
    @DisplayName("디스카운트된 금액은 마이너스가 될 수 없다.")
    void testMinusDiscountedAmount() {
        FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 1000);
        assertEquals(0, voucher.discount(900));
    }

    @Test
    @DisplayName("할인금액은 마이너스가 될 수 없다.")
//    @Disabled //스킵
    void testWithMinus() {
        assertThrows(IllegalArgumentException.class,
                () -> new FixedAmountVoucher(UUID.randomUUID(), -100));
    }

    @Test
    @DisplayName("할인금액은 마이너스가 될 수 없다.")
    void testVoucherCreation() {
        assertAll("FixedAmountVoucher creation",
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 0)),
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), -100)),
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(UUID.randomUUID(), 100000))
        );
    }
}