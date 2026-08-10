package com.yaswanth.ecommerce.order.controller;

import com.yaswanth.ecommerce.order.model.OrderRequest;
import com.yaswanth.ecommerce.order.model.OrderResponse;
import com.yaswanth.ecommerce.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/orders")
    public List<OrderResponse> getOrders() {
        return orderService.getOrders();
    }

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }
}
