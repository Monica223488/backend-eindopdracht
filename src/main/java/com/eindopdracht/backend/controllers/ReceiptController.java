package com.eindopdracht.backend.controllers;

import com.eindopdracht.backend.dtos.ReceiptRequestDto;
import com.eindopdracht.backend.dtos.ReceiptResponseDto;
import com.eindopdracht.backend.mapper.ReceiptMapper;
import com.eindopdracht.backend.models.Receipt;
import com.eindopdracht.backend.services.ReceiptService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    private final ReceiptService service;

    public ReceiptController(ReceiptService service) {this.service = service; }

    @PostMapping
    public ResponseEntity<ReceiptResponseDto> createReceipt (@Valid @RequestBody ReceiptRequestDto receiptRequestDto){

        Receipt receipt = this.service.createReceipt(receiptRequestDto);
        ReceiptResponseDto receiptResponseDto = ReceiptMapper.toResponseDto(receipt);

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + receipt.getId()).toUriString());
        return ResponseEntity.created(uri).body(receiptResponseDto);

    }
}
