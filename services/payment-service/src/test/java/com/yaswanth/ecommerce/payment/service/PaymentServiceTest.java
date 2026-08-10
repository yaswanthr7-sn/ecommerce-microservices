package com.yaswanth.ecommerce.payment.service;

import com.yaswanth.ecommerce.payment.PaymentStatus;
import com.yaswanth.ecommerce.payment.model.PaymentRequest;
import com.yaswanth.ecommerce.payment.model.PaymentResponse;
import com.yaswanth.ecommerce.payment.entity.Payment;
import com.yaswanth.ecommerce.payment.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    public void getPaymentsTest() {
        when(paymentRepository.findAll())
                .thenReturn(List.of());
        List<PaymentResponse> result = paymentService.getPayments();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void createPaymentTest() {

        UUID orderId = UUID.randomUUID();

        PaymentRequest paymentRequest = new PaymentRequest(
                orderId,
                new BigDecimal("59999"),
                "INR"
        );

        when(paymentRepository.save(any(Payment.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        PaymentResponse result =
                paymentService.createPayment(paymentRequest);

        assertEquals(orderId, result.getOrderId());
        assertEquals(new BigDecimal("59999"), result.getAmount());
        assertEquals(PaymentStatus.PENDING, result.getStatus());

        verify(paymentRepository).save(any(Payment.class));
    }

}
