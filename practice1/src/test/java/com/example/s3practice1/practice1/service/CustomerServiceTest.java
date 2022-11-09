package com.example.s3practice1.practice1.service;

import com.example.s3practice1.practice1.domain.Customer;
import com.example.s3practice1.practice1.domain.Product;
import com.example.s3practice1.practice1.exception.CustomerAlreadyExistsException;
import com.example.s3practice1.practice1.exception.CustomerNotFoundException;
import com.example.s3practice1.practice1.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer1, customer2;
    List<Customer> listCustomer;
    Product product1, product2;

    @BeforeEach
    public void setUp(){
        product1 = new Product(22,"AC","Cool");
        product2 = new Product(12,"Car","Good");
        customer1 = new Customer(200,"Mira","8024512456",product1);
        customer2 = new Customer(201,"Tina","9625481374",product2);
        listCustomer = Arrays.asList(customer1,customer2);
    }

    @AfterEach
    public void tearDown(){
        customer1 = null;
        customer2 = null;
    }

    @Test
    public void saveCustomerTest() throws CustomerAlreadyExistsException {
        when(customerRepository.findById(customer1.getCustomerId())).thenReturn(Optional.ofNullable(null));
        when(customerRepository.save(any())).thenReturn(customer1);
        assertEquals(customer1,customerService.addCustomerDetails(customer1));
        verify(customerRepository,times(1)).save(any());
        verify(customerRepository,times(1)).findById(any());
    }

    @Test
    public void saveCustomerTestFailure(){
        when(customerRepository.findById(customer1.getCustomerId())).thenReturn(Optional.ofNullable(customer1));
        assertThrows(CustomerAlreadyExistsException.class, ()->customerService.addCustomerDetails(customer1));
        verify(customerRepository,times(0)).save(any());
        verify(customerRepository,times(1)).findById(any());
    }

    @Test
    public void deleteCustomerTest() throws CustomerNotFoundException {
        when(customerRepository.findById(customer1.getCustomerId())).thenReturn(Optional.ofNullable(customer1));
        boolean flag = customerService.deleteCustomerById(customer1.getCustomerId());
        assertEquals(true,flag);
        verify(customerRepository,times(1)).deleteById(any());
        verify(customerRepository,times(1)).findById(any());
    }
}
