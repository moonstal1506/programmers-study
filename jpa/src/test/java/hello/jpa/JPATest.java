package hello.jpa;

import hello.jpa.repository.domain.CustomerEntity;
import hello.jpa.repository.domain.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
public class JPATest {

    @Autowired
    CustomerRepository repository;

    @BeforeEach
    void setUp(){}

    @AfterEach
    void tearDown(){
        repository.deleteAll();
    }

    @Test
    void INSERT_TEST() {
        //given
        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);
        customer.setFirstName("sujeong");
        customer.setLastName("moon");

        //when
        repository.save(customer);

        //then
        CustomerEntity entity = repository.findById(1L).get();
        log.info("{} {}", entity.getFirstName(), entity.getLastName());
    }

    @Test
    @Transactional
    void UPDATE_TEST() {
        //given
        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);
        customer.setFirstName("sujeong");
        customer.setLastName("moon");
        repository.save(customer);

        //when
        CustomerEntity entity = repository.findById(1L).get();
        entity.setFirstName("sj");
        entity.setLastName("m");

        //then
        CustomerEntity updated = repository.findById(1L).get();
        log.info("{} {}", entity.getFirstName(), entity.getLastName());
    }
}
