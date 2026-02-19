package com.eindopdracht.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
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

    public Order(String paperType, int amount, float price, String status, String size) {
        this.paperType = paperType;
        this.amount = amount;
        this.price = price;
        this.status = status;
        this.size = size;
    }

}
