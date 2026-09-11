
FROM eclipse-temurin:21-jre
LABEL authors="yaswanth"

WORKDIR /app

COPY target/order-service-0.0.2-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]