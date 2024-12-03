# Step 1: Build the JAR file
FROM gradle:8.11-jdk21 AS build

USER root
RUN apt-get update && apt-get install -y maven
USER gradle

WORKDIR /app

# Copy the entire project
COPY --chown=gradle:gradle . .

# Install the parent POM into the local repository
WORKDIR /app
RUN mvn clean install -N -DskipTests

# Move to the banking-api directory and build the JAR
WORKDIR /app/banking-api
RUN mvn clean install -DskipTests

# Move to front directory and build the JAR
WORKDIR /app/front
RUN ./gradlew clean build --no-daemon

# Step 2: Create the final image
FROM openjdk:21-jdk
WORKDIR /app

# Copy the built JAR file
COPY --from=build /app/front/build/libs/front-1.0-SNAPSHOT.jar front.jar

# Run the app
ENTRYPOINT ["java", "-jar", "front.jar"]
