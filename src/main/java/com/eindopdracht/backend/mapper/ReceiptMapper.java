package com.eindopdracht.backend.mapper;

import com.eindopdracht.backend.dtos.ReceiptRequestDto;
import com.eindopdracht.backend.dtos.ReceiptResponseDto;
import com.eindopdracht.backend.models.Receipt;

public class ReceiptMapper {

    public static Receipt toEntity(ReceiptRequestDto receiptRequestDto) {
        Receipt receipt = new Receipt(
                receiptRequestDto.summary,
                receiptRequestDto.customerInformation,
                receiptRequestDto.appointmentInformation
        );
        return receipt;
    }

    public static ReceiptResponseDto toResponseDto (Receipt receipt){
        ReceiptResponseDto receiptResponseDto = new ReceiptResponseDto();
        receiptResponseDto.summary = receipt.getSummary();
        receiptResponseDto.customerInformation = receipt.getCustomerInformation();
        receiptResponseDto.appointmentInformation = receipt.getAppointmentInformation();
        return receiptResponseDto;
    }
}
