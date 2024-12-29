# First Stage: Build the application
FROM maven:3.8-openjdk-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Second Stage: Use a lightweight runtime image
FROM tomcat:9.0-jdk17-temurin
WORKDIR /usr/local/tomcat
COPY --from=builder /app/target/WeatherProxyService.war webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
