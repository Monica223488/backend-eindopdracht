package com.eindopdracht.backend.controllers;

import com.eindopdracht.backend.dtos.AppointmentRequestDto;
import com.eindopdracht.backend.dtos.AppointmentResponseDto;
import com.eindopdracht.backend.mapper.AppointmentMapper;
import com.eindopdracht.backend.models.Appointment;
import com.eindopdracht.backend.services.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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
}
