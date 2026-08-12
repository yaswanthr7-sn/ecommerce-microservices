package com.yaswanth.ecommerce.payment.component.strategy;

import com.yaswanth.ecommerce.payment.enums.PaymentStatus;
import com.yaswanth.ecommerce.payment.interfaces.PaymentStrategy;
import com.yaswanth.ecommerce.payment.model.PaymentRequest;
import org.springframework.stereotype.Component;

@Component
public class WalletPaymentStrategy implements PaymentStrategy {

    @Override
    public PaymentStatus process(PaymentRequest request) {
        // Wallet payment logic
        return PaymentStatus.SUCCESS;
    }
}
