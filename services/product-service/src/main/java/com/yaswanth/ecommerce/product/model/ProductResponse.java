package com.yaswanth.ecommerce.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ProductResponse {

    private UUID id;
    private String name;
    private BigDecimal price;
    private String currency;

}
