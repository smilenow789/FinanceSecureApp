# Build-Phase: Maven kompiliert den Code
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Run-Phase: Das fertige JAR wird in ein leichtes Image kopiert
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Cloud Run erwartet Web-Traffic standardmäßig auf Port 8080
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]