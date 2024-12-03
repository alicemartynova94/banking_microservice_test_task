# Step 1: Build the JAR file
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the entire project
COPY . .

# Install the parent POM into the local repository
WORKDIR /app
RUN mvn clean install -N -DskipTests

# Move to the eureka-server directory and build the JAR
WORKDIR /app/eureka-server
RUN mvn clean package -DskipTests

# Step 2: Create the final image
FROM openjdk:21-jdk
WORKDIR /app

# Copy the built JAR file
COPY --from=build /app/eureka-server/target/eureka-server-0.0.1-SNAPSHOT.jar eureka-server.jar

# Run the app
ENTRYPOINT ["java", "-jar", "eureka-server.jar"]
