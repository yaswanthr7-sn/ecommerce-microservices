package com.yaswanth.ecommerce.payment.repository;

import com.yaswanth.ecommerce.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}
