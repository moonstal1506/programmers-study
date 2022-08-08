package hello.jpa;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Slf4j
@SpringBootTest
public class JDBCTest {

    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:tcp://localhost/~/test";
    static final String USER = "sa";
    static final String PASS = "";

    static final String DROP_TABLE_SQL = "DROP TABLE customers IF EXISTS";
    static final String CREATE_TABLE_SQL = "CREATE TABLE customers(id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))";
    static final String INSERT_SQL = "INSERT INTO customers (id, first_name, last_name) VALUES(1, 'sujeong', 'moon')";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void jdbc_sample() {
        try {
            Class.forName(JDBC_DRIVER);

            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            log.info("Connection 획득");
            Statement statement = connection.createStatement();
            log.info("Statement 획득");

            log.info("쿼리 실행");
            statement.executeUpdate(DROP_TABLE_SQL);
            statement.executeUpdate(CREATE_TABLE_SQL);

            statement.executeUpdate(INSERT_SQL);
            ResultSet resultSet = statement.executeQuery("SELECT * FROM customers WHERE id = 1");

            while(resultSet.next()) {
                String fullName = resultSet.getString("first_name") + " " + resultSet.getString("last_name");
                log.info("fullName:{}",fullName);
            }

            log.info("반납");
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void jdbcTemplate_sample() {
        jdbcTemplate.update(DROP_TABLE_SQL);
        jdbcTemplate.update(CREATE_TABLE_SQL);

        jdbcTemplate.update(INSERT_SQL);

        String fullName = jdbcTemplate.queryForObject(
                "SELECT * FROM customers WHERE id = 1",
                (resultSet, i) -> resultSet.getString("first_name") + " " + resultSet.getString("last_name")
        );
        log.info("fullName:{}",fullName);
    }
}