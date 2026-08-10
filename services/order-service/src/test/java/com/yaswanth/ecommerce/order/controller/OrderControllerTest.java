package com.yaswanth.ecommerce.order.controller;

import com.yaswanth.ecommerce.order.OrderStatus;
import com.yaswanth.ecommerce.order.model.OrderRequest;
import com.yaswanth.ecommerce.order.model.OrderResponse;
import com.yaswanth.ecommerce.order.service.OrderService;
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

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Test
    void getOrdersTest() throws Exception {

        when(orderService.getOrders())
                .thenReturn(List.of());

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        verify(orderService).getOrders();
    }

    @Test
    void createOrderTest() throws Exception {

        UUID orderId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        OrderResponse response =
                new OrderResponse(
                        orderId,
                        productId,
                        2,
                        new BigDecimal("59999"),
                        "INR",
                        OrderStatus.CREATED
                );

        when(orderService.createOrder(any(OrderRequest.class)))
                .thenReturn(response);

        String requestJson = """
                {
                    "productId": "%s",
                    "quantity": 2,
                    "amount": 59999,
                    "currency": "INR"
                }
                """.formatted(productId);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(orderId.toString()))
                .andExpect(jsonPath("$.productId").value(productId.toString()))
                .andExpect(jsonPath("$.quantity").value(2))
                .andExpect(jsonPath("$.amount").value(59999))
                .andExpect(jsonPath("$.currency").value("INR"))
                .andExpect(jsonPath("$.status").value("CREATED"));

        verify(orderService).createOrder(any(OrderRequest.class));
    }

    @Test
    void createOrderValidationTest() throws Exception {

        String invalidRequest = """
                {
                    "productId": null,
                    "quantity": 0,
                    "amount": -10,
                    "currency": ""
                }
                """;

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest());

        verify(orderService, never())
                .createOrder(any(OrderRequest.class));
    }

    @Test
    void createOrderValidationReturnsCustomErrorTest() throws Exception {

        String invalidRequest = """
                {
                    "productId": null,
                    "quantity": 0,
                    "amount": -10,
                    "currency": ""
                }
                """;

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.path").value("/orders"))
                .andExpect(jsonPath("$.errors.productId").exists())
                .andExpect(jsonPath("$.errors.quantity").exists())
                .andExpect(jsonPath("$.errors.amount").exists())
                .andExpect(jsonPath("$.errors.currency").exists());

        verify(orderService, never())
                .createOrder(any(OrderRequest.class));
    }
}