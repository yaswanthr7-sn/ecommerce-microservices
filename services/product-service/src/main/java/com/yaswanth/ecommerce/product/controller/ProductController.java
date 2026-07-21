package com.yaswanth.ecommerce.product.controller;

import com.yaswanth.ecommerce.product.dto.ProductResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @GetMapping("/products")
    public List<ProductResponse> getProducts(){
        return new ArrayList<>();
    }
}
