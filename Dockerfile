FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src

RUN find src -type f \\( -name "*.properties" -o -name "*.java" -o -name "*.html" \\) | while read -r f; do \
      iconv -f UTF-8 -t UTF-8 "$f" >/dev/null 2>&1 || { iconv -f WINDOWS-1252 -t UTF-8 "$f" > "$f.utf8" && mv "$f.utf8" "$f"; }; \
    done

RUN mvn clean package -DskipTests -B -q

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

COPY --from=build /app/target/*.jar app.jar

ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75 -XX:+UseSerialGC -XX:TieredStopAtLevel=1"

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "exec java -Dserver.port=${PORT:-8080} -jar app.jar"]