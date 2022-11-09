package com.example.s3practice1.practice1.service;

import com.example.s3practice1.practice1.domain.Customer;
import com.example.s3practice1.practice1.exception.CustomerAlreadyExistsException;
import com.example.s3practice1.practice1.exception.CustomerNotFoundException;
import com.example.s3practice1.practice1.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{

    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }


    @Override
    public Customer addCustomerDetails(Customer customer) throws CustomerAlreadyExistsException {
        if(customerRepository.findById(customer.getCustomerId()).isPresent()){
            throw new CustomerAlreadyExistsException();
        }
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomer() throws CustomerNotFoundException {
        return customerRepository.findAll();
    }

    @Override
    public boolean deleteCustomerById(int customerId) throws CustomerNotFoundException {
        boolean result = false;

        if(customerRepository.findById(customerId).isEmpty()){
            throw  new CustomerNotFoundException();
        }else {
            customerRepository.deleteById(customerId);
            result = true;
        }
        return result;
    }

    @Override
    public List<Customer> getAllCustomerByProductSamsung(String productName) throws CustomerNotFoundException {

        if(customerRepository.findAllCustomerByProductName(productName).isEmpty()){
            throw new CustomerNotFoundException();
        }
        return customerRepository.findAllCustomerByProductName(productName);
    }
}
