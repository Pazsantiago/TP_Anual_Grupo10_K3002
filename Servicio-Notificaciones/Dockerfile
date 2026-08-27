# --- Etapa 1: Compilación ---
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copia el pom directamente (ya que estás parado al lado de él)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia el código fuente
COPY src ./src
RUN mvn clean package -DskipTests

# --- Etapa 2: Imagen de producción liviana ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

