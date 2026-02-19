package com.eindopdracht.backend.repositories;

import com.eindopdracht.backend.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findAppointmentById(Long id);
}
