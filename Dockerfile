FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the compiled JAR file from the target directory
COPY target/client-api-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app is running on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]