package com.eindopdracht.backend.services;

import com.eindopdracht.backend.dtos.OrderRequestDto;
import com.eindopdracht.backend.exceptions.ResourceNotFoundException;
import com.eindopdracht.backend.mapper.OrderMapper;
import com.eindopdracht.backend.models.Customer;
import com.eindopdracht.backend.models.Order;
import com.eindopdracht.backend.repositories.CustomerRepository;
import com.eindopdracht.backend.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository repos;
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository repos, CustomerRepository customerRepository) {
        this.repos = repos;
        this.customerRepository = customerRepository;
    }

    public Order createOrder(OrderRequestDto orderRequestDto) {

        Customer customer = customerRepository
                .findById(orderRequestDto.customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer niet gevonden"));

        Order order = OrderMapper.toEntity(orderRequestDto);
        order.setCustomer(customer);

        return repos.save(order);
    }

    public List<Order> getAllOrders() {
        return repos.findAll();
    }

    public Order getSingleOrder(UUID id){
        return this.repos.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order" + id + "not found!" ));
    }
}
