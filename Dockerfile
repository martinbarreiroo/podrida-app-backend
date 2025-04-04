FROM eclipse-temurin:21-jdk as build
WORKDIR /app

# Copy gradle files
COPY build.gradle gradlew ./
COPY gradle ./gradle
RUN chmod +x ./gradlew

# Cache dependencies
RUN ./gradlew dependencies --no-daemon || true

# Copy source and build
COPY . .
RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy New Relic agent files
RUN mkdir -p /usr/local/newrelic
COPY newrelic/newrelic.jar /usr/local/newrelic/newrelic.jar
COPY newrelic/newrelic.yml /usr/local/newrelic/newrelic.yml

# Copy the app jar
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

# Run app with New Relic agent
ENTRYPOINT ["java", "-javaagent:/usr/local/newrelic/newrelic.jar", "-jar", "app.jar"]
