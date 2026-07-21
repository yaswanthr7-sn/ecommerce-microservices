# Engineering Challenges

Reusable questions to revisit while learning. Project-specific decisions belong in `architecture-decisions.md`.

## Java and Design

1. When should a concept be an entity, a value object, or simply a process?
2. When does an interface add useful abstraction, and when is a single concrete class clearer?
3. How do you choose between `Long`, UUID, ULID, and database-generated identifiers?
4. What makes an API contract different from a persistence model, and why should they not be coupled?

## Spring Boot

5. How does Spring Boot auto-configuration decide to create a bean such as a `DataSource`?
6. What belongs in a controller, service, repository, DTO, and entity? Where should business rules live?
7. Why does Spring Boot manage dependency versions, and when is overriding one appropriate?
8. How would you test a controller, service, and repository differently?

## Databases and Distributed Systems

9. How do indexes improve reads, and what trade-offs do they impose on writes and storage?
10. How do transactions, isolation levels, locks, and optimistic locking prevent inconsistent data?
11. What happens when one service calls another service that is slow or unavailable? Consider timeouts, retries, circuit breakers, bulkheads, fallbacks, idempotency, and observability.
12. How can multiple services preserve business consistency without a shared database transaction? Compare synchronous workflows, events, Saga, Outbox, and compensation.

## Delivery and Operations

13. What is the difference between a Docker image, container, volume, and network?
14. What makes a service independently buildable, deployable, observable, and scalable?
15. Which metrics, logs, and traces would you inspect first when an API becomes slow or starts failing?
