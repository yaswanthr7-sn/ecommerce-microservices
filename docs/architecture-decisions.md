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

## Decision 008: Use a Monorepo for the Microservices

**Status:** Accepted

**Decision:** Keep all microservices in this repository under `services/`. Each service remains an independent Maven project and can be built and deployed independently. Shared repository configuration, such as `.gitignore`, `.gitattributes`, and project documentation, remains at the repository root.

**Reason:** A monorepo keeps cross-service documentation and local development manageable without preventing independent builds or deployments. It avoids repository-management overhead while the project is growing.

## Decision 009: Use Spring Boot Managed Dependencies

**Status:** Accepted

**Decision:** Use the latest stable, supported Spring Boot 4.1.x release as the parent for new services. Rely on Spring Boot's dependency management for managed libraries and omit dependency versions unless an override is intentional and documented.

**Reason:** The Spring Boot BOM supplies a tested set of compatible dependency versions, reducing version conflicts and making upgrades deliberate.

## Decision 010: Package Services as Executable JARs

**Status:** Accepted

**Decision:** Package each Spring Boot microservice as an executable JAR with its embedded server, rather than as a WAR for deployment to an external application server.

**Reason:** Executable JARs keep services independently runnable and deployable, and are the appropriate packaging model for later Docker and container-orchestrated deployments.

## Decision 011: Introduce Service Interfaces Only When Needed

**Status:** Accepted

**Decision:** Do not create `Service`/`ServiceImpl` pairs by default. Introduce an interface only when it serves a concrete need, such as multiple implementations, an explicit boundary, a testing strategy, or a framework requirement.

**Reason:** A single implementation does not justify an additional abstraction. This keeps the codebase focused while preserving the option to add an interface when it solves a real problem.

## Decision 012: Each Microservice Owns Its Database

**Status:** Accepted

**Decision:** Each microservice owns a separate database and must not access another service's tables directly. During development, the separate databases may run on the same PostgreSQL server or container.

**Reason:** Data ownership keeps service boundaries clear and prevents direct database coupling. Sharing a PostgreSQL server locally avoids unnecessary operational overhead without compromising database-per-service isolation.
