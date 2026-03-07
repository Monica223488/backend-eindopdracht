package com.eindopdracht.backend.repositories;

import com.eindopdracht.backend.models.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PhotoRepository extends JpaRepository<Photo, UUID> {
}
