package com.eindopdracht.backend.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentRequestDto {
    @NotNull
    public LocalDate appointmentDate;
    @NotNull
    public LocalTime appointmentTime;
}
