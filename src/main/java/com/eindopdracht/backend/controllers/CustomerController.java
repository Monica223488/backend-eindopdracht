package com.eindopdracht.backend.controllers;

import com.eindopdracht.backend.dtos.CustomerRequestDto;
import com.eindopdracht.backend.dtos.CustomerResponseDto;
import com.eindopdracht.backend.mapper.CustomerMapper;
import com.eindopdracht.backend.models.Customer;
import com.eindopdracht.backend.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {this.service = service;}

    @PostMapping
    public ResponseEntity<CustomerResponseDto> createCustomer (@RequestBody CustomerRequestDto customerRequestDto){

       Customer customer = this.service.createCustomer(customerRequestDto);
       CustomerResponseDto customerResponseDto = CustomerMapper.toResponseDto(customer);

       URI uri = URI.create(
               ServletUriComponentsBuilder
                       .fromCurrentRequest()
                       .path("/" + customer.getId()).toUriString());
            return ResponseEntity.created(uri).body(customerResponseDto);

    }

    @GetMapping("/{id}")
    public CustomerResponseDto getCustomer(@PathVariable UUID id) {
        Customer customer = service.getCustomer(id);
        return CustomerMapper.toResponseDto(customer);
    }

    @GetMapping
    public List<CustomerResponseDto> getCustomers() {
        return service.getAllCustomers()
                .stream()
                .map(CustomerMapper::toResponseDto)
                .toList();
    }

}
