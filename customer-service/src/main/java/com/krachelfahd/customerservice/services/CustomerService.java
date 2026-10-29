package com.krachelfahd.customerservice.services;

import com.krachelfahd.customerservice.entities.Customer;
import com.krachelfahd.customerservice.repository.CustomerRepository;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @McpTool(description = "Get All customers")

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }
    @McpTool(description = "Find a customer by id")
    public Customer findCustomerById(@McpToolParam(description = "The customer id")Long id){
        return customerRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Customer Not found"));
    }
    @McpTool(description = "Save a new customer")
    public Customer saveCustomer(@McpToolParam(description = "The Customer to save")Customer customer){
        return customerRepository.save(customer);
    }
}
