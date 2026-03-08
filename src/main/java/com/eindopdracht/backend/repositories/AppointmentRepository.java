package com.eindopdracht.backend.repositories;

import com.eindopdracht.backend.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    List<Appointment> findAppointmentById(UUID id);
}
