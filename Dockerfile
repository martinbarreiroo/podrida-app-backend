FROM eclipse-temurin:21-jdk as build
WORKDIR /app

# Copy only gradle files first for better caching
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
RUN chmod +x ./gradlew

# Cache dependencies
RUN ./gradlew dependencies --no-daemon

# Copy source code and build
COPY . .
RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]