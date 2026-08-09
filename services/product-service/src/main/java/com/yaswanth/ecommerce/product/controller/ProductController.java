package com.yaswanth.ecommerce.product.controller;

import com.yaswanth.ecommerce.product.dto.ProductRequest;
import com.yaswanth.ecommerce.product.dto.ProductResponse;
import com.yaswanth.ecommerce.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public List<ProductResponse> getProducts() {
        return productService.getProducts();
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }
}
