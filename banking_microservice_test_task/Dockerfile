FROM openjdk:21-jdk
ARG JAR_FILE=target/*.jar
COPY ./target/banking_microservice_test_task-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]