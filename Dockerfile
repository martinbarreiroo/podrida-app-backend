FROM eclipse-temurin:21-jdk as build
WORKDIR /app

# Copy newrelic.yml
COPY newrelic.yml ./

# Copy gradle files
COPY build.gradle gradlew ./
COPY gradle ./gradle
RUN chmod +x ./gradlew

# Cache dependencies
RUN ./gradlew dependencies --no-daemon || true

# Copy source code and build
COPY . .
RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app

# Download New Relic Java agent
ENV NEW_RELIC_VERSION=8.10.0
RUN curl -L https://download.newrelic.com/newrelic/java-agent/newrelic-agent/${NEW_RELIC_VERSION}/newrelic-java.zip -o newrelic-java.zip && \
    unzip newrelic-java.zip -d /newrelic && \
    rm newrelic-java.zip

# Copy config file
COPY --from=build /app/newrelic.yml /newrelic/newrelic.yml

# Copy application jar
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

# Launch app with New Relic agent
ENTRYPOINT ["java", "-javaagent:/newrelic/newrelic-agent.jar", "-jar", "app.jar"]
