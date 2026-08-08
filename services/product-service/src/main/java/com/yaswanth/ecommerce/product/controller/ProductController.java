package com.yaswanth.ecommerce.product.controller;

import com.yaswanth.ecommerce.product.ProductService;
import com.yaswanth.ecommerce.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public List<ProductResponse> getProducts(){
        return productService.getProducts();
    }
}
