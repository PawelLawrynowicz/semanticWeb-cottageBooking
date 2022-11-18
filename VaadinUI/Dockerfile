FROM openjdk:17-jdk-slim
COPY target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app.jar"]
