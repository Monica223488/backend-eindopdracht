package com.eindopdracht.backend.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Table(name="roles")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role {

    @Id
    private UUID id;
    @Column(nullable = false, unique = true)
    private String name;

    public Role(String name){
        this.name = name;
    }

    @PrePersist
    public void generateId(){
        if (id == null){
            id = UUID.randomUUID();
        }
    }
}
