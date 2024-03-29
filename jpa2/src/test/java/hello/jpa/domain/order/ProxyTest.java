package hello.jpa.domain.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@SpringBootTest
public class ProxyTest {

    @Autowired
    EntityManagerFactory emf;

    private String uuid = UUID.randomUUID().toString();

    @BeforeEach
    void setUp() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // 주문 엔티티
        Order order = new Order();
        order.setUuid(uuid);
        order.setMemo("부재시 전화주세요.");
        order.setOrderDatetime(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.OPENED);

        entityManager.persist(order);

        // 회원 엔티티
        Member member = new Member();
        member.setName("moon");
        member.setNickName("닉네임");
        member.setAge(33);
        member.setAddress("주소");
        member.setDescription("설명");

        member.addOrder(order);
        entityManager.persist(member);
        transaction.commit();
    }


    @Test
    void proxy() {
        EntityManager entityManager = emf.createEntityManager();
        Order order = entityManager.find(Order.class, uuid);

        Member member = order.getMember();
        log.info("member use before is-loaded: {}", emf.getPersistenceUnitUtil().isLoaded(member)); //Lazy일때 false, member객체는 proxy 객체이다
        member.getNickName();
        log.info("member use after is-loaded: {}", emf.getPersistenceUnitUtil().isLoaded(member)); //member 객체가 entity
    }

    @Test
    void move_persist() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        Order order = entityManager.find(Order.class, uuid);//영속상태

        transaction.begin();

        OrderItem item = new OrderItem(); //준영속상태
        item.setQuantity(10);
        item.setPrice(1000);

        order.addOrderItem(item); //cascade = CascadeType.ALL-> 영속성 전이를 통해서 영속상태로 바뀌었다.
        transaction.commit(); //flush()
        entityManager.clear();
        //------------------------------------

        Order order2 = entityManager.find(Order.class, uuid);
        transaction.begin();
        order2.getOrderItems().remove(0);//고아상태 flush 순간 rds에서도 삭제를 하겠다.
        transaction.commit(); //flush
    }

}
