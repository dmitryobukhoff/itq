FROM maven:3.9.8-amazoncorretto-17 as build

COPY src src
COPY pom.xml pom.xml

RUN mvn clean package

FROM openjdk:17-jdk-slim

RUN adduser --system spring-boot && addgroup --system spring-boot && adduser spring-boot spring-boot
USER spring-boot

WORKDIR /app

COPY --from=build target/OrderService-0.0.1-SNAPSHOT.jar ./order.jar

ENTRYPOINT ["java", "-jar", "./order.jar"]