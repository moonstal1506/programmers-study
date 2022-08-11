package hello.jpa.domain.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    void test() {
        String uuid = UUID.randomUUID().toString();
        Order order = new Order();
        order.setUuid(uuid);
        order.setOrderStatus(OrderStatus.OPENED);
        order.setOrderDatetime(LocalDateTime.now());
        order.setCreatedBy("moon");
        order.setCratedAt(LocalDateTime.now());

        orderRepository.save(order);
        Order order1 = orderRepository.findById(uuid).get();
        List<Order> all = orderRepository.findAll();

        orderRepository.findAllByOrderStatus(OrderStatus.OPENED);
        orderRepository.findAllByOrderStatusOrderByOrderDatetime(OrderStatus.OPENED);
        orderRepository.findByMemo("----");
    }
}