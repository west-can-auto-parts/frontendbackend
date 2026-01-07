# Use Eclipse Temurin (official OpenJDK replacement)
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy project files
COPY . .

# Give execution permission to mvnw
RUN chmod +x ./mvnw

# Build the application
RUN ./mvnw clean package -Dmaven.test.skip=true

# Expose application port
EXPOSE 8081

# Run the application
CMD ["java", "-jar", "target/demo21-0.0.1-SNAPSHOT.jar"]
