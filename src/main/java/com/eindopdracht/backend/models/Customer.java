package com.eindopdracht.backend.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name= "customers")
public class Customer {

    @Id
    private UUID id;
    @Setter
    private String name;
    @Setter
    private String phoneNumber;

    @PrePersist
    public void generateId(){
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

    public Customer(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }


}
