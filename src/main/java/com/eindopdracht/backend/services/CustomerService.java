package com.eindopdracht.backend.services;

import com.eindopdracht.backend.dtos.CustomerRequestDto;
import com.eindopdracht.backend.exceptions.ResourceNotFoundException;
import com.eindopdracht.backend.mapper.CustomerMapper;
import com.eindopdracht.backend.models.Customer;
import com.eindopdracht.backend.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository repos;

    public CustomerService(CustomerRepository repos) {this.repos = repos;}

    public Customer createCustomer(CustomerRequestDto customerRequestDto) {
        return this.repos.save(CustomerMapper.toEntity(customerRequestDto));
    }

    public Customer getCustomer(UUID id){
        return repos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer " + id + " not found!"));
    }

    public List<Customer> getAllCustomers(){
        return repos.findAll();
    }
}