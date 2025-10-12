FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY src/main/resources/http.json src/main/resources/http.json

COPY webRoot /app/WebRoot

COPY target/Http_Server-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
