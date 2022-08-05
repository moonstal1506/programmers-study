package com.example.order.customer.controller;

//public record CreateCustomerRequest(String email, String name){}
public class CreateCustomerRequest {

    private final String email;
    private final String name;

    public CreateCustomerRequest(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
