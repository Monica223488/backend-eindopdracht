package com.eindopdracht.backend.controllers;

import com.eindopdracht.backend.dtos.ReceiptRequestDto;
import com.eindopdracht.backend.models.Customer;
import com.eindopdracht.backend.models.Order;
import com.eindopdracht.backend.models.Receipt;
import com.eindopdracht.backend.repositories.CustomerRepository;
import com.eindopdracht.backend.repositories.OrderRepository;
import com.eindopdracht.backend.repositories.ReceiptRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import tools.jackson.databind.ObjectMapper;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class ReceiptControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Order savedOrder;

    private Receipt receipt1;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
            receiptRepository.deleteAll();
            orderRepository.deleteAll();
            customerRepository.deleteAll();

            Customer customer = new Customer(
                    "Bas",
                    "0623456789",
                    "bas@email.nl"
            );
            customer = customerRepository.save(customer);

            Order order = new Order (
                    "Mat",
                    1,
                    19.99f,
                    "CREATED",
                    "15x20"
            );
            order.setCustomer(customer);

            savedOrder = orderRepository.save(order);

    }

    @Test
    void createReceipt_shouldReturnCreatedReceipt() throws Exception {
        ReceiptRequestDto requestDto = new ReceiptRequestDto();
        requestDto.orderId = savedOrder.getId();

        mockMvc.perform(post("/receipts")
                        .with(user("test@test.nl").roles("EMPLOYEE"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.summary").value("Receipt for order" + savedOrder.getId()))
                .andExpect(jsonPath("$.customerInformation").value("Bas - 0623456789"))
                .andExpect(jsonPath("$.appointmentInformation").value("No appointment planned"));
    }

    @Test
    void getSingleReceipt_shouldReturnReceipt_whenIdExists() throws Exception {
        receipt1 = new Receipt(
                "summary",
                "customer information",
                "12-4-2027, 10:00",
                savedOrder
        );
        receipt1 = receiptRepository.save(receipt1);

        mockMvc.perform(get("/receipts/{id}", receipt1.getId())
                        .with(user("test@test.nl").roles("EMPLOYEE")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(receipt1.getId().toString()))
                .andExpect(jsonPath("$.summary").value("summary"))
                .andExpect(jsonPath("$.customerInformation").value("customer information"))
                .andExpect(jsonPath("$.appointmentInformation").value("12-4-2027, 10:00"));
    }
}
