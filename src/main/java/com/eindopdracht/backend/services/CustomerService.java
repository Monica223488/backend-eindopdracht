package com.eindopdracht.backend.services;

import com.eindopdracht.backend.dtos.CustomerRequestDto;
import com.eindopdracht.backend.exceptions.ResourceNotFoundException;
import com.eindopdracht.backend.mapper.CustomerMapper;
import com.eindopdracht.backend.models.Customer;
import com.eindopdracht.backend.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository repos;

    public CustomerService(CustomerRepository repos) {this.repos = repos;}

    public Customer createCustomer(CustomerRequestDto customerRequestDto) {
        return this.repos.save(CustomerMapper.toEntity(customerRequestDto));
    }
        public Customer getSingleCustomer(int id){
            return this.repos.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer" + id + "not found!"));


    }
}