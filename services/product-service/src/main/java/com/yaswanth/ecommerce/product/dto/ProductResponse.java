package com.yaswanth.ecommerce.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class ProductResponse {

    UUID id;
    String name;
    BigDecimal price;
    String currency;

}
