package com.eindopdracht.backend.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public class OrderRequestDto {
    @NotEmpty
    public String paperType;
    @Positive
    public int amount;
    @Positive
    public float price;
    @NotEmpty
    public String status;
    @NotEmpty
    public String size;
    @NotNull
    public UUID customerId;
}
