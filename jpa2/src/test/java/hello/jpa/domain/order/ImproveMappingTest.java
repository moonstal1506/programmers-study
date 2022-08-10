package hello.jpa.domain.order;

import lombok.extern.slf4j.Slf4j;
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
public class ImproveMappingTest {

    @Autowired
    private EntityManagerFactory emf;

    @Test
    void inheritance_test() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Food food = new Food();
        food.setPrice(2000);
        food.setStockQuantity(200);
        food.setChef("백종원");

        entityManager.persist(food);

        transaction.commit();
    }

    @Test
    void mapped_supper_class_test() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderStatus(OrderStatus.OPENED);
        order.setMemo("메모");
        order.setOrderDatetime(LocalDateTime.now());

        order.setCreatedBy("moon");
        order.setCratedAt(LocalDateTime.now());
        entityManager.persist(order);
        transaction.commit();
    }


}
