package com.yaswanth.ecommerce.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ProductResponse {

    UUID id;
    String name;
    BigDecimal price;
    String currency;

}
