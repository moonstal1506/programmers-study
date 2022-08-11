package hello.jpa.order.service;

import hello.jpa.domain.order.Order;
import hello.jpa.domain.order.OrderRepository;
import hello.jpa.order.converter.OrderConverter;
import hello.jpa.order.dto.OrderDto;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderConverter orderConverter;
    private final OrderRepository orderRepository;

    @Transactional //entity(영속화된 객체) 트랜잭션 밖으로 보내는 것 좋지 않음(의도하지 않은 쿼리 발생), dto 객체 만들어 통신
    public String save(OrderDto dto) {
        //1. dto -> entity 변환(준영속)
        Order order = orderConverter.convertOrder(dto);
        //2. orderRepository.save(entity)-> 영속화
        Order entity = orderRepository.save(order);
        //3. 결과 반환
        return entity.getUuid();
    }

    @Transactional
    public OrderDto findOne(String uuid) throws NotFoundException {
        //1. 조회를 위한 키값 인자로 받기
        //2. orderRepository.findById(uuid)-> 조회(영속화된 엔티티)
        return orderRepository.findById(uuid)
                .map(orderConverter::convertOrderDto)//3. entity -> dto
                .orElseThrow(() -> new NotFoundException("주문을 찾을 수 없습니다."));
    }

    @Transactional
    public Page<OrderDto> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(orderConverter::convertOrderDto);
    }
}
