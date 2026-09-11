
FROM eclipse-temurin:21-jre
LABEL authors="yaswanth"

WORKDIR /app

COPY target/product-service-0.0.2-SNAPSHOT.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]