package com.eindopdracht.backend.services;

import com.eindopdracht.backend.dtos.ReceiptRequestDto;
import com.eindopdracht.backend.exceptions.ResourceNotFoundException;
import com.eindopdracht.backend.mapper.ReceiptMapper;
import com.eindopdracht.backend.models.Receipt;
import com.eindopdracht.backend.repositories.ReceiptRepository;
import org.springframework.stereotype.Service;

@Service
public class ReceiptService {

    private final ReceiptRepository repos;

    public ReceiptService(ReceiptRepository repos) {this.repos = repos;}

    public Receipt createReceipt(ReceiptRequestDto receiptRequestDto) {
        return this.repos.save(ReceiptMapper.toEntity(receiptRequestDto));
    }

    public Receipt getSingleReceipt(int id){
        return this.repos.findById(id).orElseThrow(()-> new ResourceNotFoundException("Receipt"+ id + "not found!"));
    }
}
