package com.yaswanth.ecommerce.payment.component;

import com.yaswanth.ecommerce.payment.model.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderEventConsumer {

    @KafkaListener(
            topics = "order-created",
            groupId = "payment-service"
    )
    public void consumeOrderCreated(OrderCreatedEvent event) {

        log.info(
                "Received OrderCreatedEvent: orderId={}, amount={}, currency={}",
                event.orderId(),
                event.amount(),
                event.currency()
        );

        // Process payment
    }
}
