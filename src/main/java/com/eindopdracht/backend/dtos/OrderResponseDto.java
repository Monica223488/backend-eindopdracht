package com.eindopdracht.backend.dtos;

import java.util.UUID;

public class OrderResponseDto {
    public UUID id;
    public String paperType;
    public int amount;
    public float price;
    public String status;
    public String size;
}
