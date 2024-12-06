FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/banking-api-0.1.0-SNAPSHOT.jar /app/banking-api.jar
EXPOSE 8081
CMD ["java", "-jar", "banking-api.jar"]