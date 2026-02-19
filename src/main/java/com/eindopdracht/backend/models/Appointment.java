package com.eindopdracht.backend.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name ="appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Setter
    private LocalDate AppointmentDate;
    @Setter
    private LocalTime AppointmentTime;

    public Appointment(LocalDate date, LocalTime time) {
        this.AppointmentDate = date;
        this.AppointmentTime = time;
    }
}
