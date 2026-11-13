# ---------- Etapa 1: build ----------
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Cachea dependencias (solo se re-descargan si cambia el pom.xml)
COPY pom.xml .
RUN mvn -B -q dependency:go-offline

COPY src ./src

# Algunos archivos (ej. application.properties) estan guardados en Windows-1252 y Maven
# espera UTF-8. Convierte solo los que no sean UTF-8 valido.
RUN find src -type f \( -name "*.properties" -o -name "*.java" -o -name "*.html" -o -name "*.css" -o -name "*.js" \) | while read -r f; do \
      iconv -f UTF-8 -t UTF-8 "$f" >/dev/null 2>&1 || { iconv -f WINDOWS-1252 -t UTF-8 "$f" > "$f.utf8" && mv "$f.utf8" "$f"; }; \
    done

# Tests afuera: el contextLoads necesita Keycloak y los otros servicios levantados
RUN mvn -B -q clean package -DskipTests

# ---------- Etapa 2: runtime ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

RUN useradd --system --create-home appuser
COPY --from=build /app/target/*.jar app.jar
USER appuser

# Render inyecta PORT (por defecto 10000); si no existe, usa 8080 (local)
ENV PORT=8080
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75 -XX:+UseSerialGC"
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "exec java -Dserver.port=${PORT} -jar app.jar"]
