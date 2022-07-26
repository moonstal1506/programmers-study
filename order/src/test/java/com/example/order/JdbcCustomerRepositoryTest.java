package com.example.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class JdbcCustomerRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepositoryTest.class);
    JdbcCustomerRepository repository = new JdbcCustomerRepository();

    @BeforeEach
    void beforeEach() {
        repository.deleteAllCustomers();
    }

    @Test
    void Jdbc연결() {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "1234");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from customers");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                UUID customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                logger.info("customer id -> {}, name -> {}", customerId, name);
            }
        } catch (SQLException e) {
            logger.error("Got error",e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            }catch (SQLException e) {
                logger.error("Got error while closing connection",e);
            }
        }
    }

    @Test
    @DisplayName("자바10 AutoCloseable 구현")
    void AutoCloseable() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "1234");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from customers");
             ) {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                UUID customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                //Timestamp로 바로 사용x LocalDateTime으로 바꿔서 사용하기
                //null이 될 수 있는 부분 null체크하고 바꿔주기
                LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                logger.info("customer id -> {}, name -> {}, createdAt -> {}", customerId, name, createdAt);
            }
        } catch (SQLException e) {
            logger.error("Got error",e);
        }
    }

    @Test
    void findNames() {
        List<String> names = repository.findNames("tester01");
        names.forEach(v -> logger.info("Found name : {}", v));
    }

    @Test
    void findId() {
        UUID customerId = UUID.randomUUID();
        logger.info("created customerId -> {}",customerId);
        logger.info("created UUID version -> {}",customerId.version());

        repository.insertCustomer(customerId, "new-user1", "new-user1@gmail.com");
        repository.findAllIds().forEach(v -> logger.info("Found customerId : {} and version: {}", v, v.version()));//4
    }

    @Test
    void insert() {
        repository.insertCustomer(UUID.randomUUID(), "new-user1", "new-user1@gmail.com");
        repository.insertCustomer(UUID.randomUUID(), "new-user2", "new-user2@gmail.com");

        List<String> names = repository.findAllName();
        names.forEach(v -> logger.info("Found name : {}", v));//4
    }

    @Test
    void update() {
        UUID customerId = UUID.randomUUID();
        repository.insertCustomer(customerId, "new-user1", "new-user1@gmail.com");
        repository.findAllName().forEach(v -> logger.info("Found name : {}", v));
        repository.updateCustomerName(customerId, "update-user1");
        repository.findAllName().forEach(v -> logger.info("Found name : {}", v));
    }

    @Test
    void deleteAll() {
        int count = repository.deleteAllCustomers();
        logger.info("deleted count -> {}", count);
    }

}
