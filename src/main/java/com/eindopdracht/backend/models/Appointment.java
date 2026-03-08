package com.eindopdracht.backend.models;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name ="appointments")
public class Appointment {

    @Id
    private UUID id;
    @Setter
    private LocalDate appointmentDate;
    @Setter
    private LocalTime appointmentTime;

    @PrePersist
    public void generateId(){
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

    public Appointment(LocalDate date, LocalTime time) {
        this.appointmentDate = date;
        this.appointmentTime = time;
    }
}
