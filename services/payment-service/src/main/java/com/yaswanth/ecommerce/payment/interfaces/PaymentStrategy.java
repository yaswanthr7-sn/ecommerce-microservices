package com.yaswanth.ecommerce.payment.interfaces;

import com.yaswanth.ecommerce.payment.enums.PaymentStatus;
import com.yaswanth.ecommerce.payment.model.PaymentRequest;

public interface PaymentStrategy {

    PaymentStatus process(PaymentRequest request);
}