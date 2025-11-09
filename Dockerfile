# Step 1: Build the app (use Maven)
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn -q -e -DskipTests package

ENV PORT=10000

# Step 2: Run the application
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 10000
CMD ["java", "-jar", "app.jar"]