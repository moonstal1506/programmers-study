package com.example.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.UUID;

public class JdbcCustomerRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepositoryTest.class);

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
                logger.info("customer id -> {}, name -> {}", customerId, name);
            }
        } catch (SQLException e) {
            logger.error("Got error",e);
        }
    }
}
