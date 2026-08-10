package com.yaswanth.ecommerce.payment.controller;

import com.yaswanth.ecommerce.payment.model.PaymentRequest;
import com.yaswanth.ecommerce.payment.model.PaymentResponse;
import com.yaswanth.ecommerce.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/payments")
    public List<PaymentResponse> getPayments() {
        return paymentService.getPayments();
    }

    @PostMapping("/payments")
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponse createPayment(@Valid @RequestBody PaymentRequest paymentRequest) {
        return paymentService.createPayment(paymentRequest);
    }
}
