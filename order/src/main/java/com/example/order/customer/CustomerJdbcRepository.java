package com.example.order.customer;

import com.example.order.JdbcCustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.nio.ByteBuffer;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * DriverManager->Datasource
 */
@Repository
public class CustomerJdbcRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private final DataSource dataSource;

    public CustomerJdbcRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Customer insert(Customer customer) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("insert into  customers(customer_id, name, email, created_at) values (UUID_TO_BIN(?),?,?,?)");
        ) {
            statement.setBytes(1, customer.getCustomerId().toString().getBytes());
            statement.setString(2, customer.getName());
            statement.setString(3, customer.getEmail());
            statement.setTimestamp(4, Timestamp.valueOf(customer.getCreated_At()));
            int executeUpdate = statement.executeUpdate();
            if (executeUpdate != 1) {
                throw new RuntimeException("Noting was inserted");
            }
            return customer;
        } catch (SQLException throwable) {
            logger.error("Got error", throwable);
            throw new RuntimeException(throwable);
        }
    }

    @Override
    public Customer update(Customer customer) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "update customers " +
                             "set name = ?, email = ?, last_login_at = ? " +
                             "where customer_id = UUID_TO_BIN(?)");
        ) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            statement.setTimestamp(3, customer.getLastLoginAt() != null ? Timestamp.valueOf(customer.getLastLoginAt()) : null);
            statement.setBytes(4, customer.getCustomerId().toString().getBytes());
            int executeUpdate = statement.executeUpdate();
            if (executeUpdate != 1) {
                throw new RuntimeException("Noting was updated");
            }
            return customer;
        } catch (SQLException throwable) {
            logger.error("Got error", throwable);
            throw new RuntimeException(throwable);
        }
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> allCustomers = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("select * from customers");
             ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                mapToCustomer(allCustomers, resultSet);
            }

        } catch (SQLException throwable) {
            logger.error("Got error", throwable);
            //런타임 예외로 바꾸기
            throw new RuntimeException(throwable);
        }
        return allCustomers;
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        List<Customer> allCustomers = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("select * from customers where customer_id = UUID_TO_BIN(?)");
        ) {
            statement.setBytes(1, customerId.toString().getBytes());
            logger.info("statement -> {}", statement);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    mapToCustomer(allCustomers, resultSet);
                }
            }
        } catch (SQLException throwable) {
            logger.error("Got error",throwable);
            throw new RuntimeException(throwable);
        }
        return allCustomers.stream().findFirst();
    }

    @Override
    public Optional<Customer> findByName(String name) {
        List<Customer> allCustomers = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("select * from customers where name = ?");
        ) {
            statement.setString(1, name);
            logger.info("statement -> {}", statement);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    mapToCustomer(allCustomers, resultSet);
                }
            }
        } catch (SQLException throwable) {
            logger.error("Got error",throwable);
            throw new RuntimeException(throwable);
        }
        return allCustomers.stream().findFirst();
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        List<Customer> allCustomers = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("select * from customers where email = ?");
        ) {
            statement.setString(1, email);
            logger.info("statement -> {}", statement);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    mapToCustomer(allCustomers, resultSet);
                }
            }
        } catch (SQLException throwable) {
            logger.error("Got error",throwable);
            throw new RuntimeException(throwable);
        }
        return allCustomers.stream().findFirst();
    }

    @Override
    public void deleteAll() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "1234");
             PreparedStatement statement = connection.prepareStatement("delete from customers");
        ) {
            statement.executeUpdate();
        } catch (SQLException throwable) {
            logger.error("Got error",throwable);
            throw new RuntimeException(throwable);
        }
    }

    private UUID toUUID(byte[] bytes) throws SQLException {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(),byteBuffer.getLong());
    }

    private void mapToCustomer(List<Customer> allCustomers, ResultSet resultSet) throws SQLException {
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        String customerName = resultSet.getString("name");
        String email = resultSet.getString("email");
        LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        allCustomers.add(new Customer(customerId,customerName,email,lastLoginAt,createdAt));
    }
}
