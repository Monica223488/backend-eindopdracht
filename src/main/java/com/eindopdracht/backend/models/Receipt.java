package com.eindopdracht.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@Table(name="receipts")
public class Receipt {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String summary;
    @Setter
    private String customerInformation;
    @Setter
    private String appointmentInformation;

    public Receipt(String summary, String customerInformation, String appointmentInformation) {
        this.summary = summary;
        this.customerInformation = customerInformation;
        this.appointmentInformation = appointmentInformation;
    }


}
