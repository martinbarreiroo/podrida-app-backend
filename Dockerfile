FROM eclipse-temurin:21-jdk as build
WORKDIR /app

COPY build.gradle gradlew ./
COPY gradle ./gradle
RUN chmod +x ./gradlew

RUN ./gradlew dependencies --no-daemon || true

COPY . .
RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app

# Accept files from build args (they will come from the GitHub Action)
ARG NEW_RELIC_JAR
ARG NEW_RELIC_YML
COPY --from=build /app/build/libs/*.jar app.jar

COPY ${NEW_RELIC_JAR} /usr/local/newrelic/newrelic.jar
COPY ${NEW_RELIC_YML} /usr/local/newrelic/newrelic.yml

EXPOSE 8080
ENTRYPOINT ["java", "-javaagent:/usr/local/newrelic/newrelic.jar", "-Dnewrelic.config.file=/usr/local/newrelic/newrelic.yml", "-jar", "app.jar"]
