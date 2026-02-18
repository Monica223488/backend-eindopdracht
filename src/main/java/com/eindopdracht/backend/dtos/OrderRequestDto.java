package com.eindopdracht.backend.dtos;

import jakarta.validation.constraints.NotEmpty;

public class OrderRequestDto {
    @NotEmpty
    public String paperType;
    @NotEmpty
    public int amount;
    @NotEmpty
    public float price;
    @NotEmpty
    public String status;
    @NotEmpty
    public String size;
}
