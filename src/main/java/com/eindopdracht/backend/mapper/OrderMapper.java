package com.eindopdracht.backend.mapper;

import com.eindopdracht.backend.dtos.OrderRequestDto;
import com.eindopdracht.backend.dtos.OrderResponseDto;
import com.eindopdracht.backend.models.Order;

public class OrderMapper {

    public static Order toEntity(OrderRequestDto orderRequestDto) {
        Order order = new Order(
                orderRequestDto.paperType,
                orderRequestDto.amount,
                orderRequestDto.price,
                orderRequestDto.status,
                orderRequestDto.size
                );
        return order;
    }

    public static OrderResponseDto toResponseDto(Order order) {
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.status = order.getStatus();
        return orderResponseDto;
    }
}
