package hello.jpa.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,String> {
    List<Order> findAllByOrderStatus(OrderStatus orderStatus);
    List<Order> findAllByOrderStatusOrderByOrderDatetime(OrderStatus orderStatus);

    @Query("select o from Order  as o where o.memo like %?1%")
    Optional<Order> findByMemo(String memo);
}
