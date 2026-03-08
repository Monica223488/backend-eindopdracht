package com.eindopdracht.backend.mapper;


import com.eindopdracht.backend.dtos.ReceiptResponseDto;
import com.eindopdracht.backend.models.Receipt;

public class ReceiptMapper {

    public static ReceiptResponseDto toResponseDto (Receipt receipt){
        ReceiptResponseDto receiptResponseDto = new ReceiptResponseDto();
        receiptResponseDto.id = receipt.getId();
        receiptResponseDto.summary = receipt.getSummary();
        receiptResponseDto.customerInformation = receipt.getCustomerInformation();
        receiptResponseDto.appointmentInformation = receipt.getAppointmentInformation();
        return receiptResponseDto;
    }
}
