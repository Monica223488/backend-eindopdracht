package com.eindopdracht.backend.repositories;

import com.eindopdracht.backend.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findCustomerById(Long id);
}
