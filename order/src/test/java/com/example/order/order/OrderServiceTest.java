package com.example.order.order;

import com.example.order.voucher.FixedAmountVoucher;
import com.example.order.voucher.MemoryOrderRepository;
import com.example.order.voucher.MemoryVoucherRepository;
import com.example.order.voucher.VoucherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    class OrderRepositoryStub implements OrderRepository {
        @Override
        public Order insert(Order order) {
            return null;
        }
    }

    @Test
    @DisplayName("stub: 오더가 생성되어야 한다.")
    void createOrder() {
        //given
        MemoryVoucherRepository voucherRepository = new MemoryVoucherRepository();
        FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        voucherRepository.insert(voucher);
        OrderService orderService = new OrderService(new VoucherService(voucherRepository), new OrderRepositoryStub());
//        OrderService orderService = new OrderService(voucherService, new MemoryOrderRepository());

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

    @Test
    @DisplayName("mock: 오더가 생성되어야 한다.")
    void createOrderByMock() {
        //given
        VoucherService voucherServiceMock = mock(VoucherService.class);
        OrderRepository orderRepositoryMock = mock(OrderRepository.class);
        FixedAmountVoucher voucher = new FixedAmountVoucher(UUID.randomUUID(), 100);
        when(voucherServiceMock.getVoucher(voucher.getVoucherId())).thenReturn(voucher);
        OrderService orderService = new OrderService(voucherServiceMock, orderRepositoryMock);

        //when
        Order order = orderService.createOrder(
                UUID.randomUUID(),
                List.of(new OrderItem(UUID.randomUUID(), 200, 1)),
                voucher.getVoucherId());
        //then
        assertThat(order.totalAmount(), is(100L));
        assertThat(order.getVoucher().isEmpty(), is(false));

        //행위관점에서 생각
        verify(voucherServiceMock).getVoucher(voucher.getVoucherId());
        verify(voucherServiceMock).useVoucher(voucher);
        verify(orderRepositoryMock).insert(order);

        //특정 순서 보장
        InOrder inOrder = inOrder(voucherServiceMock, orderRepositoryMock);
        inOrder.verify(voucherServiceMock).getVoucher(voucher.getVoucherId());
        inOrder.verify(orderRepositoryMock).insert(order);
        inOrder.verify(voucherServiceMock).useVoucher(voucher);
    }
}