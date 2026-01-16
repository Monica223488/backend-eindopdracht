package com.eindopdracht.backend.controllers;

import com.eindopdracht.backend.models.Appointment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders/{id}/appointment")
public class AppointmentController {
    @PostMapping
    public ResponseEntity<Appointment> createAppointment (@RequestBody Appointment appointment){

        return new ResponseEntity<>(appointment, HttpStatus.CREATED);
    }
}
