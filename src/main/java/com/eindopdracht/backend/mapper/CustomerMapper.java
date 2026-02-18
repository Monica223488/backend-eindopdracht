package com.eindopdracht.backend.mapper;

import com.eindopdracht.backend.dtos.CustomerRequestDto;
import com.eindopdracht.backend.dtos.CustomerResponseDto;
import com.eindopdracht.backend.models.Customer;

public class CustomerMapper {

    public static Customer toEntity(CustomerRequestDto customerRequestDto) {
        Customer customer = new Customer(
                customerRequestDto.name,
                customerRequestDto.phoneNumber
        );
        return customer;
    }

    public static CustomerResponseDto toResponseDto (Customer customer){
        CustomerResponseDto customerResponseDto = new CustomerResponseDto();
        customerResponseDto.name = customer.getName();
        customerResponseDto.phoneNumber = customer.getPhoneNumber();
        return customerResponseDto;

    }
}
