package com.yaswanth.ecommerce.order.model;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderCreatedEvent(
        UUID orderId,
        BigDecimal amount,
        String currency
) {
}
