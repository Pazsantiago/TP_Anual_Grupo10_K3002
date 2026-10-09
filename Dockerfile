# Imagen base con Java
FROM eclipse-temurin:17-jdk

# Copiar el JAR generado por Maven
COPY target/Logistica-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto de tu aplicación
EXPOSE 8081

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
