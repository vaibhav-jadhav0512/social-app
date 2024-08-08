# Stage 1: Build
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory
WORKDIR /app

# Copy the Maven pom.xml and the source code
COPY pom.xml .
COPY src /app/src


# Package the application
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/config-server-0.0.1-SNAPSHOT.jar /app/config-server.jar

# Expose the application port
EXPOSE 8765

# Run the application
ENTRYPOINT ["java", "-jar", "/app/config-server.jar"]
