FROM maven:3.9.11-eclipse-temurin-21-alpine

WORKDIR /spring

COPY . .

RUN mvn clean install

CMD ["mvn", "spring-boot:run"]