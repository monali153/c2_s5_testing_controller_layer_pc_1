package com.example.s3practice1.practice1.repository;

import com.example.s3practice1.practice1.domain.Customer;
import com.example.s3practice1.practice1.domain.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer;
    private Product  product;

    @BeforeEach
    public void setUp(){
        product = new Product(10,"Mobile","It is portable to use");
        customer = new Customer(5,"Jenny","8412578634",product);
    }

    @AfterEach
    public void tearDown(){
        product = null;
        customer = null;
        customerRepository = null;
    }

    @Test
    @DisplayName("Test case for saving customer object")
    public void saveCustomerTest(){
        customerRepository.save(customer);
        Customer customer1 = customerRepository.findById(customer.getCustomerId()).get();
        assertNotNull(customer1);
        assertEquals(customer.getCustomerId(),customer1.getCustomerId());

    }

    @Test
    @DisplayName("Test case for retreiving all customer object")
    public void fetchCustomerTest(){
        customerRepository.insert(customer);
        List<Customer> list = customerRepository.findAll();
        assertEquals(5,list.size());
        assertEquals("Mona",list.get(0).getCustomerName());
    }

    @Test
    @DisplayName("Test case for deleting customer object")
    public void deleteCustomerTest(){
        Customer customer1 = customerRepository.findById(customer.getCustomerId()).get();
        customerRepository.delete(customer1);
        assertEquals(Optional.empty(),customerRepository.findById(customer.getCustomerId()));
    }
}
