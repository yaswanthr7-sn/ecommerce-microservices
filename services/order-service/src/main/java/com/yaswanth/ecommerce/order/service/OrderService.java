package com.yaswanth.ecommerce.order.service;

import com.yaswanth.ecommerce.order.OrderStatus;
import com.yaswanth.ecommerce.order.PaymentStatus;
import com.yaswanth.ecommerce.order.component.OrderEventProducer;
import com.yaswanth.ecommerce.order.component.PaymentClient;
import com.yaswanth.ecommerce.order.entity.Order;
import com.yaswanth.ecommerce.order.model.*;
import com.yaswanth.ecommerce.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentClient paymentClient;
    private final OrderEventProducer orderEventProducer;

    public List<OrderResponse> getOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::convertOrderToOrderResponse)
                .toList();
    }

    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {

        Order order = convertOrderRequestToOrder(orderRequest);
        order.setStatus(OrderStatus.PAYMENT_PENDING);

        orderRepository.save(order);

        PaymentResponse paymentResponse =
                paymentClient.makePayment(
                        new PaymentRequest(
                                order.getId(),
                                order.getAmount(),
                                order.getCurrency()
                        ));

        if (paymentResponse.getStatus() == PaymentStatus.SUCCESS) {
            order.setStatus(OrderStatus.CONFIRMED);
        } else if (paymentResponse.getStatus() == PaymentStatus.FAILED) {
            order.setStatus(OrderStatus.FAILED);
        }

        Order savedOrder = orderRepository.save(order);

        if (savedOrder.getStatus() == OrderStatus.CONFIRMED) {
            OrderCreatedEvent event = new OrderCreatedEvent(
                    savedOrder.getId(),
                    savedOrder.getAmount(),
                    savedOrder.getCurrency()
            );

            orderEventProducer.publishOrderCreated(event);
        }

        return convertOrderToOrderResponse(savedOrder);
    }

    private OrderResponse convertOrderToOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getProductId(),
                order.getQuantity(),
                order.getAmount(),
                order.getCurrency(),
                order.getStatus());
    }

    private Order convertOrderRequestToOrder(OrderRequest orderRequest) {
        return Order.builder()
                .productId(orderRequest.getProductId())
                .quantity(orderRequest.getQuantity())
                .amount(orderRequest.getAmount())
                .currency(orderRequest.getCurrency())
                .status(OrderStatus.CREATED)
                .build();
    }
}
