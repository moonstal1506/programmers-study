package hello.jpa.order.service;

import hello.jpa.domain.order.Order;
import hello.jpa.domain.order.OrderRepository;
import hello.jpa.order.converter.OrderConverter;
import hello.jpa.order.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderConverter orderConverter;
    private final OrderRepository orderRepository;

    @Transactional //entity 트랜잭션 밖으로 보내는 것 좋지 않음, dto 객체 만들어 통신
    public String save(OrderDto dto) {
        //1. dto -> entity 변환(준영속)
        Order order = orderConverter.convertOrder(dto);
        //2. orderRepository.save(entity)-> 영속화
        Order entity = orderRepository.save(order);
        //3. 결과 반환
        return entity.getUuid();
    }

    public void findAll() {

    }

    public void findOne() {

    }
}
