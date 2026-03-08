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
@Table(name = "orders")
public class Order {

    @Id
    private UUID id;
    @Setter
    private String paperType;
    @Setter
    private int amount;
    @Setter
    private float price;
    @Setter
    private String status;
    @Setter
    private String size;

    @PrePersist
    public void generateId(){
        if (id == null){
            id = UUID.randomUUID();
        }
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "customer_id", nullable = false)
    @Setter
    private Customer customer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    @Setter
    private Appointment appointment;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "order")
    private Photobook photobook;

    @OneToOne(fetch = FetchType.LAZY, mappedBy="order")
    private Receipt receipt;

    public Order(String paperType, int amount, float price, String status, String size) {
        this.paperType = paperType;
        this.amount = amount;
        this.price = price;
        this.status = status;
        this.size = size;
    }

}
