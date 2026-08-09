package com.yaswanth.ecommerce.product.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class ProductRequest {

    @NotBlank
    @Size(max = 200)
    private String name;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal price;

    @NotBlank
    @Size(min = 3, max = 3)
    private String currency;

    @Size(max = 2000)
    private String description;

    @Min(0)
    private Integer availableQuantity;
}
