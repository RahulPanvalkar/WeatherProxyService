# First Stage: Build the application
FROM maven:3.8-openjdk-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Second Stage: Use a lightweight runtime image
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/WeatherProxyService-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java", "-jar", "WeatherProxyService-0.0.1-SNAPSHOT.jar"]
