package com.yaswanth.ecommerce.order.service;

import com.yaswanth.ecommerce.order.component.OrderEventProducer;
import com.yaswanth.ecommerce.order.enums.OrderStatus;
import com.yaswanth.ecommerce.order.enums.PaymentStatus;
import com.yaswanth.ecommerce.order.component.PaymentClient;
import com.yaswanth.ecommerce.order.entity.Order;
import com.yaswanth.ecommerce.order.enums.PaymentType;
import com.yaswanth.ecommerce.order.model.OrderRequest;
import com.yaswanth.ecommerce.order.model.OrderResponse;
import com.yaswanth.ecommerce.order.model.PaymentRequest;
import com.yaswanth.ecommerce.order.model.PaymentResponse;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private PaymentClient paymentClient;

    @Mock
    private OrderEventProducer orderEventProducer;

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
        UUID orderId = UUID.randomUUID();

        OrderRequest orderRequest = new OrderRequest(
                productId,
                2,
                new BigDecimal("59999"),
                "INR",
                PaymentType.UPI
        );

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> {
                    Order order = invocation.getArgument(0);
                    if (order.getId() == null) {
                        order.setId(orderId);
                    }
                    return order;
                });

        PaymentResponse paymentResponse =
                new PaymentResponse(
                        UUID.randomUUID(),
                        orderId,
                        new BigDecimal("59999"),
                        "INR",
                        PaymentStatus.SUCCESS
                );

        when(paymentClient.makePayment(any(PaymentRequest.class)))
                .thenReturn(paymentResponse);

        OrderResponse result = orderService.createOrder(orderRequest);

        assertEquals(productId, result.getProductId());
        assertEquals(2, result.getQuantity());
        assertEquals(new BigDecimal("59999"), result.getAmount());
        assertEquals("INR", result.getCurrency());
        assertEquals(OrderStatus.CONFIRMED, result.getStatus());

        verify(orderRepository, times(2)).save(any(Order.class));
        verify(paymentClient).makePayment(any(PaymentRequest.class));
    }

}
