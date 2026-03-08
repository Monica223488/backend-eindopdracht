package com.eindopdracht.backend.dtos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class AppointmentResponseDto {
    public UUID id;
    public LocalDate AppointmentDate;
    public LocalTime AppointmentTime;
}
