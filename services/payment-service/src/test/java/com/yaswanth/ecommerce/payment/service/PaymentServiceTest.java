package com.yaswanth.ecommerce.payment.service;

import com.yaswanth.ecommerce.payment.component.factory.PaymentStrategyFactory;
import com.yaswanth.ecommerce.payment.enums.PaymentStatus;
import com.yaswanth.ecommerce.payment.enums.PaymentType;
import com.yaswanth.ecommerce.payment.interfaces.PaymentStrategy;
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

    @Mock
    private PaymentStrategyFactory paymentStrategyFactory;

    @Mock
    private PaymentStrategy paymentStrategy;

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
                "INR",
                PaymentType.CARD
        );

        when(paymentRepository.save(any(Payment.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(paymentStrategyFactory.getStrategy(any(PaymentType.class)))
                .thenReturn(paymentStrategy);

        when(paymentStrategy.process(any(PaymentRequest.class)))
                .thenReturn(PaymentStatus.SUCCESS);

        PaymentResponse result =
                paymentService.createPayment(paymentRequest);

        assertEquals(orderId, result.getOrderId());
        assertEquals(new BigDecimal("59999"), result.getAmount());
        assertEquals(PaymentStatus.SUCCESS, result.getStatus());

        verify(paymentRepository).save(any(Payment.class));
    }

}
