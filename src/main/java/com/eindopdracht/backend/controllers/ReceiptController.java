package com.eindopdracht.backend.controllers;

import com.eindopdracht.backend.dtos.ReceiptRequestDto;
import com.eindopdracht.backend.dtos.ReceiptResponseDto;
import com.eindopdracht.backend.mapper.ReceiptMapper;
import com.eindopdracht.backend.models.Receipt;
import com.eindopdracht.backend.services.ReceiptService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    private final ReceiptService service;

    public ReceiptController(ReceiptService service) {this.service = service; }

    @PostMapping
    public ResponseEntity<ReceiptResponseDto> createReceipt (@Valid @RequestBody ReceiptRequestDto receiptRequestDto){

        Receipt receipt = this.service.createReceipt(receiptRequestDto);
        ReceiptResponseDto receiptResponseDto = ReceiptMapper.toResponseDto(receipt);

        URI uri = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(receipt.getId())
                        .toUri();

        return ResponseEntity.created(uri).body(receiptResponseDto);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceiptResponseDto> getSingleReceipt(@PathVariable UUID id) {

        Receipt receipt = service.getSingleReceipt(id);
        ReceiptResponseDto receiptResponseDto = ReceiptMapper.toResponseDto(receipt);

        return ResponseEntity.ok(receiptResponseDto);
    }


}
