FROM maven:3.9.11-amazoncorretto-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17
WORKDIR /app
COPY --from=build ./app/target/*.jar ./app.jar

ARG EUREKA_SERVER=localhost
ARG RABBITMQ_SERVER=rabbitmq-host

ENTRYPOINT exec java -jar -Dspring.profiles.active=production app.jar