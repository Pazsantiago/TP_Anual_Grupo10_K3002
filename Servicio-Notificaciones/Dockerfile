# Build the application
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy only the pom.xml first to cache dependencies (speeds up future builds)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the rest of the source code and build the fat JAR
COPY src ./src
RUN mvn clean package -DskipTests

# Create the lightweight runtime image
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the compiled JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port Spring Boot runs on
EXPOSE 8080

# Run the JAR file directly using standard java command
ENTRYPOINT ["java", "-jar", "app.jar"]

