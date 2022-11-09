package com.example.s3practice1.practice1.controller;

import com.example.s3practice1.practice1.domain.Customer;
import com.example.s3practice1.practice1.domain.Product;
import com.example.s3practice1.practice1.exception.CustomerAlreadyExistsException;
import com.example.s3practice1.practice1.service.CustomerServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CustomerServiceImpl customerService;

    @InjectMocks
    private CustomerController customerController;

    Product product;
    Customer customer;

    @BeforeEach
    public void setUp(){
        product = new Product(12,"Bike","Good speed");
        customer = new Customer(10,"Kiyara","8457912345",product);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @AfterEach
    public void tearDown(){
        product = null;
        customer = null;
    }

    private static String jsonToString(final Object ob) throws JsonProcessingException {
        String result;
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonContent = mapper.writeValueAsString(ob);
            result = jsonContent;
        } catch(JsonProcessingException e) {
            result = "JSON processing error";
        }

        return result;
    }



    @Test
    public void addCustomer() throws Exception {
        when(customerService.addCustomerDetails(any())).thenReturn(customer);
        mockMvc.perform(
                post("/custdata/api/cust").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(customer))).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        verify(customerService,times(1)).addCustomerDetails(any());
    }

    @Test
    public void addCustomerTestFailure() throws Exception {
        when(customerService.addCustomerDetails(any())).thenThrow(CustomerAlreadyExistsException.class);
        mockMvc.perform(
                post("/custdata/api/cust").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(customer))).andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print());
        verify(customerService,times(1)).addCustomerDetails(any());
    }

    @Test
    public void deleteCustomerTest() throws Exception {
        when(customerService.deleteCustomerById(anyInt())).thenReturn(true);
        mockMvc.perform(
                        delete("/custdata/api/cust/201")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonToString(customer)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(customerService,times(1)).deleteCustomerById(anyInt());
    }
}
