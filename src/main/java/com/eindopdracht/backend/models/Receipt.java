package com.eindopdracht.backend.models;

import jakarta.persistence.*;

@Entity
@Table(name="receipts")
public class Receipt {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String summary;
    private String customerInformation;
    private String appointmentInformation;

    public Receipt(){}

    public Receipt(String summary, String customerInformation, String appointmentInformation) {
        this.summary = summary;
        this.customerInformation = customerInformation;
        this.appointmentInformation = appointmentInformation;
    }

    // getters

    public Long getId() {
        return id;
    }

    public String getSummary() {
        return summary;
    }

    public String getCustomerInformation() {
        return customerInformation;
    }

    public String getAppointmentInformation() {
        return appointmentInformation;
    }

    // setters


    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setCustomerInformation(String customerInformation) {
        this.customerInformation = customerInformation;
    }

    public void setAppointmentInformation(String appointmentInformation) {
        this.appointmentInformation = appointmentInformation;
    }
}
