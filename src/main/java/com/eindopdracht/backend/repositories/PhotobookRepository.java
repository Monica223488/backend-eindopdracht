package com.eindopdracht.backend.repositories;

import com.eindopdracht.backend.models.Photobook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotobookRepository extends JpaRepository<Photobook, Integer> {
}
