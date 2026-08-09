package com.yaswanth.ecommerce.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, scale = 2, precision = 19)
    private BigDecimal price;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private Integer availableQuantity;

    @Column(nullable = false)
    private Boolean active;

    public Product(String name, BigDecimal price, String currency, String description, Integer availableQuantity) {
        this.name = name;
        this.price = price;
        this.currency = currency;
        this.description = description;
        this.availableQuantity = availableQuantity;
        this.active = true;
    }

}