package com.yaswanth.ecommerce.product.controller;

import com.yaswanth.ecommerce.product.model.ProductRequest;
import com.yaswanth.ecommerce.product.model.ProductResponse;
import com.yaswanth.ecommerce.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Test
    void getProductsTest() throws Exception {

        when(productService.getProducts())
                .thenReturn(List.of());

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(productService).getProducts();
    }

    @Test
    void createProductTest() throws Exception {

        ProductResponse response =
                new ProductResponse(
                        UUID.randomUUID(),
                        "Samsung",
                        new BigDecimal("59999"),
                        "INR"
                );

        when(productService.createProduct(any(ProductRequest.class)))
                .thenReturn(response);

        String requestJson = """
                {
                    "name": "Samsung",
                    "price": 59999,
                    "currency": "INR",
                    "description": "Test Product",
                    "availableQuantity": 10
                }
                """;

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Samsung"))
                .andExpect(jsonPath("$.price").value(59999))
                .andExpect(jsonPath("$.currency").value("INR"));

        verify(productService).createProduct(any(ProductRequest.class));
    }

    @Test
    void createProductValidationTest() throws Exception {

        String invalidRequest = """
                {
                    "name": "",
                    "price": -10,
                    "currency": "IN",
                    "description": "Test Product",
                    "availableQuantity": -1
                }
                """;

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest());

        verify(productService, never())
                .createProduct(any(ProductRequest.class));
    }

    @Test
    void createProductValidationWithBadPriceTest() throws Exception {

        String invalidRequest = """
                {
                    "name": "Samsung",
                    "price": 59999.999,
                    "currency": "INR",
                    "description": "Test Product",
                    "availableQuantity": 10
                }
                """;

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest());

        verify(productService, never())
                .createProduct(any(ProductRequest.class));
    }

    @Test
    void createProductValidationWithNoAvailableQuantityTest() throws Exception {

        String invalidRequest = """
                {
                    "name": "Samsung",
                    "price": 59999,
                    "currency": "INR",
                    "description": "Test Product"
                }
                """;

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest());

        verify(productService, never())
                .createProduct(any(ProductRequest.class));
    }

    @Test
    void createProductValidationReturnsCustomErrorTest() throws Exception {

        String invalidRequest = """
                {
                    "name": "",
                    "price": 59999.999,
                    "currency": "IN",
                    "description": "Test Product",
                    "availableQuantity": -1
                }
                """;

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.path").value("/products"))
                .andExpect(jsonPath("$.errors.name").exists())
                .andExpect(jsonPath("$.errors.price").exists())
                .andExpect(jsonPath("$.errors.currency").exists())
                .andExpect(jsonPath("$.errors.availableQuantity").exists());

        verify(productService, never())
                .createProduct(any(ProductRequest.class));
    }
}
