package com.eindopdracht.backend.services;

import com.eindopdracht.backend.dtos.OrderRequestDto;
import com.eindopdracht.backend.mapper.OrderMapper;
import com.eindopdracht.backend.models.Order;
import com.eindopdracht.backend.repositories.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository repos;

    public OrderService(OrderRepository repos) {
        this.repos = repos;
    }

    public Order createOrder(OrderRequestDto orderRequestDto) {
        return this.repos.save(OrderMapper.toEntity(orderRequestDto));
    }
}
