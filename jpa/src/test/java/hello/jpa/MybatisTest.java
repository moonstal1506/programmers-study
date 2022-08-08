package hello.jpa;

import hello.jpa.repository.CustomerMapper;
import hello.jpa.repository.domain.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
@SpringBootTest
public class MybatisTest {

    static final String DROP_TABLE_SQL = "DROP TABLE customers IF EXISTS";
    static final String CREATE_TABLE_SQL = "CREATE TABLE customers(id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CustomerMapper mapper;

    @Test
    void save_test() {
        jdbcTemplate.update(DROP_TABLE_SQL);
        jdbcTemplate.update(CREATE_TABLE_SQL);

        mapper.save(new Customer(1L, "sujeong", "moon"));
        Customer customer = mapper.findById(1L);

        log.info("fullName:{} {}", customer.getFirstName(), customer.getLastName());
    }
}