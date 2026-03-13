package com.eindopdracht.backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role {

    @Id
    private UUID id;
    @Column(nullable = false, unique = true)
    private String name;

    public void generateId(){
        if (id == null){
            id = UUID.randomUUID();
        }
    }

    public Role(String name){
        this.name = name;
    }


}
