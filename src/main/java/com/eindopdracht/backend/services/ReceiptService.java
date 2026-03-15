package com.eindopdracht.backend.services;

import com.eindopdracht.backend.dtos.ReceiptRequestDto;
import com.eindopdracht.backend.exceptions.ResourceNotFoundException;
import com.eindopdracht.backend.mapper.ReceiptMapper;
import com.eindopdracht.backend.models.Appointment;
import com.eindopdracht.backend.models.Customer;
import com.eindopdracht.backend.models.Order;
import com.eindopdracht.backend.models.Receipt;
import com.eindopdracht.backend.repositories.OrderRepository;
import com.eindopdracht.backend.repositories.ReceiptRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final OrderRepository orderRepository;

    public ReceiptService(ReceiptRepository receiptRepository, OrderRepository orderRepository) {
        this.receiptRepository = receiptRepository;
        this.orderRepository = orderRepository;
    }

    public Receipt createReceipt(ReceiptRequestDto dto) {
        Order order = orderRepository.findById(dto.orderId)
                .orElseThrow(()-> new ResourceNotFoundException("Order" + dto.orderId + " not found!"));
            Customer customer = order.getCustomer();
            Appointment appointment = order.getAppointment();

            String summary = "Receipt for order" + order.getId();

            String customerInformation = customer.getName() + " - " + customer.getPhoneNumber();

            String appointmentInformation;
            if (appointment != null) {
                appointmentInformation = appointment.getAppointmentDate() + " " + appointment.getAppointmentTime();
            } else {
                appointmentInformation = "No appointment planned";
            }

            Receipt receipt = new Receipt(summary, customerInformation, appointmentInformation);
            return receiptRepository.save(receipt);

    }

    public Receipt getSingleReceipt(UUID id){
        return receiptRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Receipt" + id + " not found"));
    }
}
