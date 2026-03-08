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
@Table(name="receipts")
public class Receipt {

    @Id
    private UUID id;
    @Setter
    private String summary;
    @Setter
    private String customerInformation;
    @Setter
    private String appointmentInformation;

    @PrePersist
    public void generateId(){
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

    @OneToOne
    @JoinColumn(name= "order_id", nullable = false, unique = true)
    private Order order;

    public Receipt(String summary, String customerInformation, String appointmentInformation) {
        this.summary = summary;
        this.customerInformation = customerInformation;
        this.appointmentInformation = appointmentInformation;
    }


}
