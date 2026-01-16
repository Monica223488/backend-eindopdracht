package com.eindopdracht.backend.controllers;

import com.eindopdracht.backend.models.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @PostMapping
    public ResponseEntity<Customer> createCustomer (@RequestBody Customer customer){

        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

}
