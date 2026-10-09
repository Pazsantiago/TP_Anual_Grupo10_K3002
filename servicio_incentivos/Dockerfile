# --- Etapa 1: Compilación (Build) ---
FROM maven:3.9.9-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copiamos el archivo de dependencias para cachearlo en Docker
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos el código fuente de tu aplicación de donaciones
COPY src src

# Compilamos y empaquetamos el proyecto omitiendo los tests para acelerar el despliegue
RUN mvn clean package -DskipTests

# --- Etapa 2: Imagen de ejecución liviana (Runtime) ---
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiamos el .jar generado en la etapa anterior y lo renombramos a app.jar
COPY --from=build /app/target/*.jar app.jar

# Render asignará y leerá el puerto automáticamente mediante la variable PORT
EXPOSE 8080

# Comando para ejecutar el microservicio
ENTRYPOINT ["java", "-jar", "app.jar"]
