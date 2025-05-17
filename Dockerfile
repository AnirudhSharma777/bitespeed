# Stage 1: Build the application using Gradle
FROM gradle:8.3.0-jdk17 AS build

# Set the working directory
WORKDIR /app

# Copy Gradle project files into the container
COPY . .

# Ensure the Gradle wrapper is executable
RUN chmod +x ./gradlew

# Build the application, skipping tests
RUN ./gradlew build -x test

# Stage 2: Run the application using OpenJDK 17
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/build/libs/*.jar bitespeed.jar

# Expose port 8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "bitespeed.jar"]