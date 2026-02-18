package com.eindopdracht.backend.repositories;

import com.eindopdracht.backend.models.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {
    List<Receipt> findOrderById(Long id);
}
