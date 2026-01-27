
# Stage 1: Build the application
FROM gradle:9.2.1-jdk17-alpine AS build
WORKDIR /app
COPY . .
# Run gradle build and skip tests
RUN gradle bootJar --no-daemon -x test

# Stage 2: Run the application
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Gradle puts the jar in build/libs instead of target/
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]