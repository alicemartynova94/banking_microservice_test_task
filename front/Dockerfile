FROM gradle:8.11-jdk21 AS build
WORKDIR /app
COPY . .
RUN ./gradlew clean build --no-daemon

FROM openjdk:21-jdk
WORKDIR /app
COPY --from=build /app/build/libs/front-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]