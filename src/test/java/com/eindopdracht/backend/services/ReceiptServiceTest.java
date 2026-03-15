package com.eindopdracht.backend.services;

import com.eindopdracht.backend.dtos.ReceiptRequestDto;
import com.eindopdracht.backend.exceptions.ResourceNotFoundException;
import com.eindopdracht.backend.models.Appointment;
import com.eindopdracht.backend.models.Customer;
import com.eindopdracht.backend.models.Order;
import com.eindopdracht.backend.models.Receipt;
import com.eindopdracht.backend.repositories.OrderRepository;
import com.eindopdracht.backend.repositories.ReceiptRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceiptServiceTest {

    @Mock
    private ReceiptRepository receiptRepository;

    @Mock
    private OrderRepository orderRepository;
    private ReceiptService receiptService;

    @BeforeEach
    void setUp() {
        receiptService = new ReceiptService(receiptRepository, orderRepository);
    }

    @Test
    void createReceipt_shouldCreateReceipt_whenOrderExistsAndAppointmentIsPresent(){
        UUID orderId = randomUUID();

        ReceiptRequestDto dto = new ReceiptRequestDto();
        dto.orderId = orderId;

        Customer customer = mock(Customer.class);
        when(customer.getName()).thenReturn("Bas");
        when(customer.getPhoneNumber()).thenReturn("0612345678");

        Appointment appointment = mock(Appointment.class);
        when(appointment.getAppointmentDate()).thenReturn(LocalDate.of(2026, 3, 14));
        when(appointment.getAppointmentTime()).thenReturn(LocalTime.of(10, 30));

        Order order = mock(Order.class);
        when(order.getId()).thenReturn(orderId);
        when(order.getCustomer()).thenReturn(customer);
        when(order.getAppointment()).thenReturn(appointment);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(receiptRepository.save(any(Receipt.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Receipt result = receiptService.createReceipt(dto);

        assertNotNull(result);

        ArgumentCaptor<Receipt> captor = ArgumentCaptor.forClass(Receipt.class);
        verify(receiptRepository).save(captor.capture());

        Receipt savedReceipt = captor.getValue();
        assertEquals("Receipt for order" + orderId, savedReceipt.getSummary());
        assertEquals("Bas - 0612345678", savedReceipt.getCustomerInformation());
        assertEquals("2026-03-14 10:30", savedReceipt.getAppointmentInformation());
    }

    @Test
    void createReceipt_shouldUseDefaultAppointmentText_whenAppointmentIsNull() {
        UUID orderId = randomUUID();

        ReceiptRequestDto dto = new ReceiptRequestDto();
        dto.orderId = orderId;

        Customer customer = mock(Customer.class);
        when(customer.getName()).thenReturn("Bas");
        when(customer.getPhoneNumber()).thenReturn("0612345678");

        Order order = mock(Order.class);
        when(order.getId()).thenReturn(orderId);
        when(order.getCustomer()).thenReturn(customer);
        when(order.getAppointment()).thenReturn(null);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(receiptRepository.save(any(Receipt.class)))
                .thenAnswer(invocation ->invocation.getArgument(0));

        Receipt result = receiptService.createReceipt(dto);

        assertNotNull(result);

        ArgumentCaptor<Receipt> captor = ArgumentCaptor.forClass(Receipt.class);
        verify(receiptRepository).save(captor.capture());

        Receipt savedReceipt = captor.getValue();
        assertEquals("Receipt for order" + orderId, savedReceipt.getSummary());
        assertEquals("Bas - 0612345678", savedReceipt.getCustomerInformation());
        assertEquals("No appointment planned", savedReceipt.getAppointmentInformation());
    }

    @Test
    void createReceipt_shouldThrowNotFound_whenOrderDoesNotExist() {
        UUID orderId = UUID.randomUUID();

        ReceiptRequestDto dto = new ReceiptRequestDto();
        dto.orderId = orderId;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows (
                ResourceNotFoundException.class,
                () -> receiptService.createReceipt(dto)
        );

        assertEquals("Order" + orderId + " not found!", ex.getMessage());
        verify(receiptRepository, never()).save(any());
    }

    @Test
    void getSingleReceipt_shouldReturnReceipt_whenIdExists() {
        UUID id = UUID.randomUUID();
        Receipt receipt = mock(Receipt.class);

        when(receiptRepository.findById(id)).thenReturn(Optional.of(receipt));

        Receipt result = receiptService.getSingleReceipt(id);

        assertSame(receipt, result);
        verify(receiptRepository).findById(id);
    }

    @Test
    void getSingleReceipt_shouldThrowNotFound_whenIdDoesNotExist(){
        UUID id = UUID.randomUUID();
        when(receiptRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                ()-> receiptService.getSingleReceipt(id)
        );

        assertEquals("Receipt" + id + " not found", ex.getMessage());
    }

}