# Architecture Decisions

This document records decisions made for the ecommerce-microservices project.

## Decision 001: Choose E-Commerce as the Domain

**Status:** Accepted

**Decision:** Build an e-commerce backend platform rather than an Airbnb-style booking platform.

**Reason:** E-commerce provides broader opportunities to learn Java backend engineering, including inventory, orders, payments, distributed systems, resilience, messaging, caching, and deployment.

## Decision 002: Use a Limited-Inventory Marketplace Model

**Status:** Accepted

**Decision:** Build a limited-inventory marketplace, including flash-sale scenarios with high-demand products.

**Reason:** Scarce inventory naturally creates practical problems around concurrency, inventory reservation, payment coordination, failure handling, and scalability.

## Decision 003: Build Backend First

**Status:** Accepted

**Decision:** Keep the backend as the primary learning focus. Introduce a minimal, AI-assisted frontend only after the backend reaches its first meaningful milestone.

**Reason:** This preserves focus on Java and Spring Boot while still allowing later end-to-end client integration.

## Decision 004: Reserve Inventory Only When Proceeding to Payment

**Status:** Accepted

**Decision:** Adding items to a cart and spending time in checkout do not reserve inventory. Inventory is reserved only when the customer proceeds to payment. The reservation lasts for three minutes and should be configurable.

**Reason:** Abandoned carts and long-running checkout sessions must not lock scarce flash-sale inventory. Inventory availability is guaranteed only after a successful reservation.

## Decision 005: Expired Reservations Release Inventory Immediately

**Status:** Accepted

**Decision:** When a reservation expires, the customer immediately loses their claim. The inventory is released and allocated on a first-successful-reservation basis.

**Reason:** This keeps limited inventory available to other customers and provides a clear first-come, first-served business rule based on successful reservation rather than click time.

## Decision 006: Model the Checkout Reservation as a Pending Order

**Status:** Tentative

**Decision:** Model the payment-window reservation as an order in a `PAYMENT_PENDING` state with an expiration time, rather than as a separate reservation business entity. Payment success confirms the order; expiry releases inventory and marks the order expired.

**Reason:** The order lifecycle currently represents the customer-facing business flow without introducing an additional entity. Revisit this if later requirements make a separate reservation lifecycle necessary.

## Decision 007: Keep Checkout Within the Cart in V1

**Status:** Accepted

**Decision:** Checkout is not a separate persisted business entity in V1. The cart owns the responsibilities of reviewing items, confirming an address, applying future discounts, and showing the final price. An order is created only when the customer proceeds to payment.

**Reason:** Checkout does not currently need an independent lifecycle; introducing it would add complexity without a business capability that the cart cannot provide. Revisit this if future requirements require resumable or separately managed checkout sessions.
