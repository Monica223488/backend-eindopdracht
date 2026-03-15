package com.eindopdracht.backend.controllers;

import com.eindopdracht.backend.dtos.AppointmentRequestDto;
import com.eindopdracht.backend.dtos.AppointmentResponseDto;
import com.eindopdracht.backend.mapper.AppointmentMapper;
import com.eindopdracht.backend.models.Appointment;
import com.eindopdracht.backend.services.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {this.service = service;}

    @PostMapping
    public ResponseEntity<AppointmentResponseDto> createAppointment (@RequestBody AppointmentRequestDto appointmentRequestDto){

        Appointment appointment = this.service.createAppointment(appointmentRequestDto);
        AppointmentResponseDto appointmentResponseDto = AppointmentMapper.toResponseDto(appointment);

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + appointment.getId()).toUriString());
        return ResponseEntity.created(uri).body(appointmentResponseDto);
    }

    @GetMapping
    public List<AppointmentResponseDto> getAppointments() {
        return service.getAllAppointments()
                .stream()
                .map(AppointmentMapper::toResponseDto)
                .toList();
    }

    @GetMapping("/{id}")
    public AppointmentResponseDto getAppointment(@PathVariable UUID id) {
        Appointment appointment = service.getSingleAppointment(id);
        return AppointmentMapper.toResponseDto(appointment);
    }
}
