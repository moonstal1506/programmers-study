package com.example.order.customer.controller;

import com.example.order.customer.model.Customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomerDto {
    private UUID customerId;
    private String name;
    private String email;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;

    public CustomerDto() {
    }

    public CustomerDto(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime createdAt) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
    }

    static CustomerDto of(Customer customer) {
        return new CustomerDto(customer.getCustomerId(),
                customer.getName(),
                customer.getEmail(),
                customer.getLastLoginAt(),
                customer.getCreatedAt());
    }

    static Customer to(CustomerDto dto) {
        return new Customer(dto.customerId,
                dto.name,
                dto.email,
                dto.lastLoginAt,
                dto.createdAt);
    }
}
