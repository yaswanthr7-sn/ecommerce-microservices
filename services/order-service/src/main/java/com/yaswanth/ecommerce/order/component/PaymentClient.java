package com.yaswanth.ecommerce.order.component;

import com.yaswanth.ecommerce.order.model.PaymentRequest;
import com.yaswanth.ecommerce.order.model.PaymentResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class PaymentClient {

    private final RestClient restClient;

    @CircuitBreaker(
            name = "paymentService",
            fallbackMethod = "paymentFallback"
    )
    public PaymentResponse makePayment(PaymentRequest request) {
        return restClient.post()
                .uri("http://localhost:8082/payments")
                .body(request)
                .retrieve()
                .body(PaymentResponse.class);
    }

    private PaymentResponse paymentFallback(
            PaymentRequest request,
            Throwable throwable) throws Exception {

        System.out.println("Payment service unavailable: "
                + throwable.getMessage());

        throw new Exception("Payment service is currently unavailable");
    }
}
