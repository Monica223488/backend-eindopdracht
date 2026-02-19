package com.eindopdracht.backend.dtos;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentRequestDto {
    @NotEmpty
    public LocalDate AppointmentDate;
    @NotEmpty
    public LocalTime AppointmentTime;
}
