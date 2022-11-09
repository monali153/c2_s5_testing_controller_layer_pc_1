package com.example.s3practice1.practice1.controller;

import com.example.s3practice1.practice1.domain.Customer;
import com.example.s3practice1.practice1.exception.CustomerAlreadyExistsException;
import com.example.s3practice1.practice1.exception.CustomerNotFoundException;
import com.example.s3practice1.practice1.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/custdata/api")
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @PostMapping("/cust")
    public ResponseEntity<?> insertDetails(@RequestBody Customer customer) throws CustomerAlreadyExistsException {
        return new ResponseEntity<>( customerService.addCustomerDetails(customer), HttpStatus.CREATED);
    }

    @GetMapping("/custs")
    public ResponseEntity<?> getAllCustomer() throws CustomerNotFoundException {

        ResponseEntity responseEntity;
        try {
            responseEntity = new ResponseEntity(customerService.getAllCustomer(),HttpStatus.OK);
        }catch (CustomerNotFoundException ex){
            throw new CustomerNotFoundException();
        }catch (Exception e){
            responseEntity = new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @DeleteMapping("/cust/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable int customerId) throws CustomerNotFoundException {
        ResponseEntity responseEntity;

        try {
            customerService.deleteCustomerById(customerId);
            responseEntity = new ResponseEntity("Successfully deleted customer",HttpStatus.OK);
        }catch (CustomerNotFoundException ex){
            throw new CustomerNotFoundException();
        }catch (Exception e){
            responseEntity = new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/customer/{productName}")
    public ResponseEntity<?> getAllCustomerHaveSamsungPhone(@PathVariable String productName) throws CustomerNotFoundException {
        ResponseEntity responseEntity;

        try {
            responseEntity = new ResponseEntity(customerService.getAllCustomerByProductSamsung(productName),HttpStatus.FOUND);
        }catch (CustomerNotFoundException ex){
            throw new CustomerNotFoundException();

        }catch (Exception e){
            responseEntity = new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
