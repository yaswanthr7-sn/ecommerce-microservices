package com.yaswanth.ecommerce.order.service;

import com.yaswanth.ecommerce.order.OrderStatus;
import com.yaswanth.ecommerce.order.entity.Order;
import com.yaswanth.ecommerce.order.model.OrderRequest;
import com.yaswanth.ecommerce.order.model.OrderResponse;
import com.yaswanth.ecommerce.order.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void getOrdersTest() {
        when(orderRepository.findAll())
                .thenReturn(List.of());
        List<OrderResponse> result = orderService.getOrders();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void createOrderTest() {

        UUID productId = UUID.randomUUID();

        OrderRequest orderRequest = new OrderRequest(
                productId,
                2,
                new BigDecimal("59999"),
                "INR"
        );

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        OrderResponse result = orderService.createOrder(orderRequest);

        assertEquals(productId, result.getProductId());
        assertEquals(2, result.getQuantity());
        assertEquals(new BigDecimal("59999"), result.getAmount());
        assertEquals("INR", result.getCurrency());
        assertEquals(OrderStatus.CREATED, result.getStatus());

        verify(orderRepository).save(any(Order.class));
    }

}
