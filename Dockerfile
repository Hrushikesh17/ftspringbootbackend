# Use a base image with JDK
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the packaged Spring Boot JAR file
COPY target/freetre-0.0.1-SNAPSHOT.jar app.jar


# Expose the application's port 
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
