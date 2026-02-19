package com.eindopdracht.backend.services;

import com.eindopdracht.backend.dtos.AppointmentRequestDto;
import com.eindopdracht.backend.exceptions.ResourceNotFoundException;
import com.eindopdracht.backend.mapper.AppointmentMapper;
import com.eindopdracht.backend.models.Appointment;
import com.eindopdracht.backend.repositories.AppointmentRepository;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    private final AppointmentRepository repos;

    public AppointmentService(AppointmentRepository repos) {this.repos = repos;}

    public Appointment createAppointment(AppointmentRequestDto appointmentRequestDto){
        return this.repos.save(AppointmentMapper.toEntity(appointmentRequestDto));
    }
    public Appointment getSingleAppointment(int id){
        return this.repos.findById(id).orElseThrow(()-> new ResourceNotFoundException("Appointment" + id + "not found!"));
    }
}
