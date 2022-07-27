package com.example.order.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
   private final UUID customerId;
   private String name;
   private final String email;
   private LocalDateTime lastLoginAt;
   private final LocalDateTime created_At;

    public Customer(UUID customerId, String name, String email, LocalDateTime created_At) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.created_At = created_At;
    }

    public Customer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, LocalDateTime created_At) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.created_At = created_At;
    }

    public void login(){
        this.lastLoginAt = LocalDateTime.now();
    }

    public void changeName(String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new RuntimeException("Name should not be blank");
        }
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public LocalDateTime getCreated_At() {
        return created_At;
    }
}
