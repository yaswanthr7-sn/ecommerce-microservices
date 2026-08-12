package com.yaswanth.ecommerce.payment.service;

import com.yaswanth.ecommerce.payment.component.factory.PaymentStrategyFactory;
import com.yaswanth.ecommerce.payment.enums.PaymentStatus;
import com.yaswanth.ecommerce.payment.interfaces.PaymentStrategy;
import com.yaswanth.ecommerce.payment.model.PaymentRequest;
import com.yaswanth.ecommerce.payment.model.PaymentResponse;
import com.yaswanth.ecommerce.payment.entity.Payment;
import com.yaswanth.ecommerce.payment.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    public final PaymentRepository paymentRepository;
    private final PaymentStrategyFactory paymentStrategyFactory;

    public List<PaymentResponse> getPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(this::convertPaymentToPaymentResponse)
                .toList();
    }

    @Transactional
    public PaymentResponse createPayment(PaymentRequest paymentRequest) {
        PaymentStrategy strategy =
                paymentStrategyFactory.getStrategy(paymentRequest.getPaymentType());

        PaymentStatus status = strategy.process(paymentRequest);
        return convertPaymentToPaymentResponse(
                paymentRepository.save(
                        convertPaymentRequestToPayment(paymentRequest)));
    }

    private PaymentResponse convertPaymentToPaymentResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getOrderId(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getStatus());
    }

    private Payment convertPaymentRequestToPayment(PaymentRequest paymentRequest) {
        return Payment.builder()
                .orderId(paymentRequest.getOrderId())
                .amount(paymentRequest.getAmount())
                .currency(paymentRequest.getCurrency())
                .status(PaymentStatus.SUCCESS)
                .paymentType(paymentRequest.getPaymentType())
                .build();
    }
}
