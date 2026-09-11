
FROM eclipse-temurin:21-jre
LABEL authors="yaswanth"

WORKDIR /app

COPY target/payment-service-0.0.2-SNAPSHOT.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]