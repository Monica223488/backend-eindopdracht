package com.eindopdracht.backend.dtos;

public class AuthResponseDto {
    public String token;

    public AuthResponseDto(String token){
        this.token = token;
    }
}
