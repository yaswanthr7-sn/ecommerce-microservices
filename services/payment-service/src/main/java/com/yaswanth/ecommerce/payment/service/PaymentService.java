package com.yaswanth.ecommerce.payment.service;

import com.yaswanth.ecommerce.payment.PaymentStatus;
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

    public List<PaymentResponse> getPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(this::convertPaymentToPaymentResponse)
                .toList();
    }

    @Transactional
    public PaymentResponse createPayment(PaymentRequest paymentRequest) {
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
                .status(PaymentStatus.PENDING)
                .build();
    }
}
