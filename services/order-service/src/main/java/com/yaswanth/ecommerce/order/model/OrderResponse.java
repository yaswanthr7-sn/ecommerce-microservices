package com.yaswanth.ecommerce.order.model;

import com.yaswanth.ecommerce.order.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Setter
public class OrderResponse {

    private UUID id;
    private UUID productId;
    private Integer quantity;
    private BigDecimal amount;
    private String currency;
    private OrderStatus status;

}
