package hello.jpa.order.service;

import hello.jpa.domain.order.OrderRepository;
import hello.jpa.domain.order.OrderStatus;
import hello.jpa.order.dto.*;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    String uuid = UUID.randomUUID().toString();

    @BeforeEach
    void save_test() {
        // Given
        OrderDto orderDto = OrderDto.builder()
                .uuid(uuid)
                .memo("문앞 보관 해주세요.")
                .orderDatetime(LocalDateTime.now())
                .orderStatus(OrderStatus.OPENED)
                .memberDto(
                        MemberDto.builder()
                                .name("이름")
                                .nickName("닉네임")
                                .address("주소")
                                .age(33)
                                .description("---")
                                .build()
                )
                .orderItemDtos(List.of(
                        OrderItemDto.builder()
                                .price(1000)
                                .quantity(100)
                                .itemDtos(List.of(
                                        ItemDto.builder()
                                                .type(ItemType.FOOD)
                                                .chef("백종원")
                                                .price(1000)
                                                .build()
                                ))
                                .build()
                ))
                .build();
        // When
        String savedUuid = orderService.save(orderDto);

        // Then
        log.info("UUID:{}", uuid);
        assertThat(uuid).isEqualTo(savedUuid);
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAll();
    }

    @Test
    void findOneTest() throws NotFoundException {
        //given
        String orderUuid = uuid;

        //when
        OrderDto one = orderService.findOne(uuid);

        //then
        assertThat(one.getUuid()).isEqualTo(orderUuid);
    }

    @Test
    void findAllTest() {
        //given
        PageRequest page = PageRequest.of(0, 10);

        //when
        Page<OrderDto> all = orderService.findAll(page);

        //then
        assertThat(all.getTotalElements()).isEqualTo(1);
    }


}