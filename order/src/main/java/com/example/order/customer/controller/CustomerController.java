package com.example.order.customer.controller;

import com.example.order.customer.service.CustomerService;
import com.example.order.customer.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ResponseBody
    @GetMapping("/api/v1/customers")
    public List<Customer> findCustomers() {
        return customerService.getAllCustomers();
    }

    @ResponseBody
    @GetMapping("/api/v1/customers/{customerId}")
    public ResponseEntity<Customer> findCustomers(@PathVariable("customerId") UUID customerId) {
        Optional<Customer> customer = customerService.getCustomer(customerId);
        return customer.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @ResponseBody
    @PostMapping("/api/v1/customers/{customerId}")
    public CustomerDto saveCustomer(@PathVariable("customerId") UUID customerId, @RequestBody CustomerDto customer) {
        logger.info("Got customer save request {}", customer);
        return customer;
    }

    //    @RequestMapping(value = "/customers",method = RequestMethod.GET)
    @GetMapping("/customers")
    public String viewCustomersPage(Model model) {
        List<Customer> allCustomers = customerService.getAllCustomers();
//        return new ModelAndView("jsp/customers", Map.of("serverTime", LocalDateTime.now(),"customers",allCustomers));
//        return new ModelAndView("views/customers", Map.of("serverTime", LocalDateTime.now(),"customers",allCustomers));
        model.addAttribute("serverTime", LocalDateTime.now());
        model.addAttribute("customers", allCustomers);
        return "views/customers";
    }

    @GetMapping("/customers/new")
    public String viewNewCustomersPage() {
        return "views/new-customers";
    }

    @GetMapping("/customers/{customerId}")
    public String findCustomer(@PathVariable("customerId") UUID customerId, Model model) {
        Optional<Customer> maybeCustomer = customerService.getCustomer(customerId);
        if (maybeCustomer.isPresent()) {
            model.addAttribute("customer", maybeCustomer.get());
            return "views/customer-details";
        }else {
            return "views/404";
        }
    }

    @PostMapping("/customers/new")
    public String addNewCustomer(CreateCustomerRequest createCustomerRequest) {
        customerService.createCustomer(createCustomerRequest.getEmail(), createCustomerRequest.getName());
        return "redirect:/customers";
    }
}
