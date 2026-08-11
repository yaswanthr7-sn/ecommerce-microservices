package com.yaswanth.ecommerce.order.component;

import com.yaswanth.ecommerce.order.model.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventProducer {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    private static final String ORDER_CREATED_TOPIC = "order-created";

    public void publishOrderCreated(OrderCreatedEvent event) {
        kafkaTemplate.send(
                ORDER_CREATED_TOPIC,
                event.orderId().toString(),
                event
        );

        log.info(
                "Published OrderCreatedEvent: orderId={}, amount={}, currency={}",
                event.orderId(),
                event.amount(),
                event.currency()
        );
    }
}
