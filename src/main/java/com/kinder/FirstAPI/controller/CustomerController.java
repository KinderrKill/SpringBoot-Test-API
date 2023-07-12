package com.kinder.FirstAPI.controller;

import com.kinder.FirstAPI.exception.UserAlreadyExistException;
import com.kinder.FirstAPI.exception.UserNotFoundException;
import com.kinder.FirstAPI.model.Customer;
import com.kinder.FirstAPI.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository repository;

    @GetMapping
    public List<Customer> findAll() {
        return this.repository.findAll();
    }

    @PostMapping
    public CustomerResponse addCustomer(@RequestBody RequestBodyCustomer customerRequest) {
        // Verify if user already exists
        Optional<Customer> tempCustomer = this.repository.findAll().stream().filter(customer -> customer.getEmail().equalsIgnoreCase(customerRequest.email)).findFirst();
        if (tempCustomer.isPresent()) throw new UserAlreadyExistException();

        Customer customer = new Customer();
        customer.setName(customerRequest.name);
        customer.setEmail(customerRequest.email);
        customer.setAge(customerRequest.age);

        this.repository.save(customer);
        return this.convertCustomerForResponse(customer);
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") int id) {
        this.repository.deleteById(id);
    }

    @PutMapping("{customerId}")
    public CustomerResponse updateCustomer(@PathVariable("customerId") int id, @RequestBody RequestBodyCustomer customerRequest) {
        Customer customer = this.repository.findById(id).orElseThrow(UserNotFoundException::new);

        if (customerRequest.name != null) customer.setName(customerRequest.name);
        if (customerRequest.email != null) customer.setEmail(customerRequest.email);
        if (customerRequest.age != 0)  customer.setAge(customerRequest.age);

        this.repository.save(customer);
        return this.convertCustomerForResponse(customer);
    }

    // Record: RequestBody Customer
    record RequestBodyCustomer(String name, String email, int age) {
    }

    // Custom response for avoid sensitive leak information
    record CustomerResponse(String name, String email, int age) {
    }

    private CustomerResponse convertCustomerForResponse(Customer customer) {
        return new CustomerResponse(customer.getName(), customer.getEmail(), customer.getAge());
    }
}
