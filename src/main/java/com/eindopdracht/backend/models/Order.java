package com.eindopdracht.backend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String paperType;
    private int amount;
    private float price;
    private String status;
    private String size;

    public Order() {}

    public Order(String paperType, int amount, float price, String status, String size) {
        this.paperType = paperType;
        this.amount = amount;
        this.price = price;
        this.status = status;
        this.size = size;
    }

    //getters
    public Long getId() {
        return id;
    }


    public String getPaperType() {
        return paperType;
    }

    public int getAmount() {
        return amount;
    }

    public float getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getSize() {
        return size;
    }

    //setters
    public void setPaperType(String paperType) {
        this.paperType = paperType;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
