package com.eindopdracht.backend.repositories;

import com.eindopdracht.backend.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    List<Customer> findCustomerById(UUID id);
}
