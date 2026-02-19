package com.eindopdracht.backend.mapper;

import com.eindopdracht.backend.dtos.AppointmentRequestDto;
import com.eindopdracht.backend.dtos.AppointmentResponseDto;
import com.eindopdracht.backend.models.Appointment;

import java.time.LocalTime;

public class AppointmentMapper {

    public static Appointment toEntity(AppointmentRequestDto appointmentRequestDto) {
        Appointment appointment = new Appointment(
                appointmentRequestDto.AppointmentDate,
                appointmentRequestDto.AppointmentTime
        );
        return appointment;
    }

    public static AppointmentResponseDto toResponseDto (Appointment appointment){
        AppointmentResponseDto appointmentResponseDto = new AppointmentResponseDto();
        appointmentResponseDto.AppointmentDate = appointment.getAppointmentDate();
        appointmentResponseDto.AppointmentTime = appointment.getAppointmentTime();
        return appointmentResponseDto;
    }


}
