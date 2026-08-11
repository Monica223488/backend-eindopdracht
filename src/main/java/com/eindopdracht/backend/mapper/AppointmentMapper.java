package com.eindopdracht.backend.mapper;

import com.eindopdracht.backend.dtos.AppointmentRequestDto;
import com.eindopdracht.backend.dtos.AppointmentResponseDto;
import com.eindopdracht.backend.models.Appointment;

import java.time.LocalTime;

public class AppointmentMapper {

    public static Appointment toEntity(AppointmentRequestDto appointmentRequestDto) {
        Appointment appointment = new Appointment(
                appointmentRequestDto.appointmentDate,
                appointmentRequestDto.appointmentTime
        );
        return appointment;
    }

    public static AppointmentResponseDto toResponseDto (Appointment appointment){
        AppointmentResponseDto appointmentResponseDto = new AppointmentResponseDto();
        appointmentResponseDto.id = appointment.getId();
        appointmentResponseDto.appointmentDate = appointment.getAppointmentDate();
        appointmentResponseDto.appointmentTime = appointment.getAppointmentTime();
        return appointmentResponseDto;
    }


}
