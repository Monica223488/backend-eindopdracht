package com.eindopdracht.backend.repositories;

import com.eindopdracht.backend.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findOrderById(Long id);
}
