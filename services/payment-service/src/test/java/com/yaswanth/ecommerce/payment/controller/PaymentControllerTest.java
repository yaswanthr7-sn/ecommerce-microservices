package com.yaswanth.ecommerce.payment.controller;

import com.yaswanth.ecommerce.payment.PaymentStatus;
import com.yaswanth.ecommerce.payment.model.PaymentRequest;
import com.yaswanth.ecommerce.payment.model.PaymentResponse;
import com.yaswanth.ecommerce.payment.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PaymentService paymentService;

    @Test
    void getPaymentsTest() throws Exception {

        when(paymentService.getPayments())
                .thenReturn(List.of());

        mockMvc.perform(get("/payments"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(paymentService).getPayments();
    }

    @Test
    void createPaymentTest() throws Exception {

        UUID paymentId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();

        PaymentResponse response =
                new PaymentResponse(
                        paymentId,
                        orderId,
                        new BigDecimal("59999"),
                        "INR",
                        PaymentStatus.PENDING
                );

        when(paymentService.createPayment(any(PaymentRequest.class)))
                .thenReturn(response);

        String requestJson = """
                {
                    "orderId": "%s",
                    "amount": 59999,
                    "currency": "INR"
                }
                """.formatted(orderId);

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(paymentId.toString()))
                .andExpect(jsonPath("$.orderId").value(orderId.toString()))
                .andExpect(jsonPath("$.amount").value(59999))
                .andExpect(jsonPath("$.status").value("PENDING"));

        verify(paymentService).createPayment(any(PaymentRequest.class));
    }

    @Test
    void createPaymentValidationTest() throws Exception {

        String invalidRequest = """
                {
                    "orderId": null,
                    "amount": -10,
                    "currency": "INR"
                }
                """;

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest());

        verify(paymentService, never())
                .createPayment(any(PaymentRequest.class));
    }

    @Test
    void createPaymentValidationReturnsCustomErrorTest() throws Exception {

        String invalidRequest = """
                {
                    "orderId": null,
                    "amount": -10,
                    "currency": "INR"
                }
                """;

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.path").value("/payments"))
                .andExpect(jsonPath("$.errors.orderId").exists())
                .andExpect(jsonPath("$.errors.amount").exists());

        verify(paymentService, never())
                .createPayment(any(PaymentRequest.class));
    }
}