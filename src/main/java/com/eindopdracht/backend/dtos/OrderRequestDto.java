package com.eindopdracht.backend.dtos;

import jakarta.validation.constraints.NotEmpty;

public class OrderRequestDto {
    @NotEmpty
    public String paperType;
    public int amount;
    public float price;
    public String status;
    public String size;
}
