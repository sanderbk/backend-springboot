# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Maven wrapper and the application source code
COPY . .

# Add execute permissions to the Maven wrapper
RUN chmod +x ./mvnw

# Set the entrypoint to use the Maven wrapper to run the Spring Boot application
ENTRYPOINT ["./mvnw", "spring-boot:run"]