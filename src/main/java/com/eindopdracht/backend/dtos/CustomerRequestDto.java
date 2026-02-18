package com.eindopdracht.backend.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class CustomerRequestDto {
    @NotEmpty
    public String name;
    @Min(value=10)
    public String phoneNumber;
}
