package com.yaswanth.ecommerce.order.repository;

import com.yaswanth.ecommerce.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
