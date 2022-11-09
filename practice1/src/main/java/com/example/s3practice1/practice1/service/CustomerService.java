package com.example.s3practice1.practice1.service;

import com.example.s3practice1.practice1.domain.Customer;
import com.example.s3practice1.practice1.exception.CustomerAlreadyExistsException;
import com.example.s3practice1.practice1.exception.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {

    Customer addCustomerDetails(Customer customer) throws CustomerAlreadyExistsException;
    List<Customer> getAllCustomer() throws CustomerNotFoundException;
    boolean deleteCustomerById(int customerId) throws CustomerNotFoundException;
    List<Customer> getAllCustomerByProductSamsung(String productName) throws CustomerNotFoundException;
}
