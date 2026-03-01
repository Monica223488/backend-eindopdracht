package com.eindopdracht.backend.controllers;

import com.eindopdracht.backend.dtos.OrderResponseDto;
import com.eindopdracht.backend.dtos.PhotoResponseDto;
import com.eindopdracht.backend.mapper.OrderMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhotobookController {

//    @GetMapping("/Photobooks")
//
//
//    @GetMapping("/Photobooks/{id}")
//    public ResponseEntity<PhotoResponseDto> getPhotoById(@PathVariable int id){
//        return ResponseEntity.ok(PhotoMapper.toResponseDto(this.service.getSinglePhoto(id)));
//    }
//
//
//    @GetMapping("/customers/{customerId}/photobooks")
//    public
}
