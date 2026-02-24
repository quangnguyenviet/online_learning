# Start with a base image containing Java runtime
FROM eclipse-temurin:21-jdk-jammy

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven build output (JAR file) into the container. Adjsut the name as needed.
COPY target/online_learning-0.0.1-SNAPSHOT.jar app.jar

# Expose the port on which your Spring Boot application will run
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]