package com.eindopdracht.backend.controllers;

import com.eindopdracht.backend.dtos.OrderRequestDto;
import com.eindopdracht.backend.dtos.OrderResponseDto;
import com.eindopdracht.backend.mapper.OrderMapper;
import com.eindopdracht.backend.models.Order;
import com.eindopdracht.backend.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDto orderRequestDto) {

        Order order = this.service.createOrder(orderRequestDto);
        OrderResponseDto orderResponseDto = OrderMapper.toResponseDto(order);

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + order.getId()).toUriString());
            return ResponseEntity.created(uri).body(orderResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable int id){
       return ResponseEntity.ok(OrderMapper.toResponseDto(this.service.getSingleOrder(id)));
    }

}
