package com.yaswanth.ecommerce.payment.component.factory;

import com.yaswanth.ecommerce.payment.component.strategy.CardPaymentStrategy;
import com.yaswanth.ecommerce.payment.component.strategy.UpiPaymentStrategy;
import com.yaswanth.ecommerce.payment.component.strategy.WalletPaymentStrategy;
import com.yaswanth.ecommerce.payment.enums.PaymentType;
import com.yaswanth.ecommerce.payment.interfaces.PaymentStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PaymentStrategyFactory {

    private final Map<PaymentType, PaymentStrategy> strategies;

    public PaymentStrategyFactory(
            CardPaymentStrategy cardPaymentStrategy,
            UpiPaymentStrategy upiPaymentStrategy,
            WalletPaymentStrategy walletPaymentStrategy) {

        this.strategies = Map.of(
                PaymentType.CARD, cardPaymentStrategy,
                PaymentType.UPI, upiPaymentStrategy,
                PaymentType.WALLET, walletPaymentStrategy
        );
    }

    public PaymentStrategy getStrategy(PaymentType paymentType) {

        PaymentStrategy strategy = strategies.get(paymentType);

        if (strategy == null) {
            throw new IllegalArgumentException(
                    "Unsupported payment type: " + paymentType
            );
        }

        return strategy;
    }
}
