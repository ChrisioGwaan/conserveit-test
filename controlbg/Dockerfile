FROM openjdk:21-jdk-slim
LABEL authors="chrisio"

WORKDIR /app

COPY target/controlbg-0.1.0.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]