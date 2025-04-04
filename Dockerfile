FROM eclipse-temurin:21-jdk as build
WORKDIR /app

# Instalar herramientas necesarias
RUN apt-get update && apt-get install -y curl unzip && rm -rf /var/lib/apt/lists/*

# Descargar el agente de New Relic
ARG NEW_RELIC_VERSION=8.10.0
RUN curl -L https://download.newrelic.com/newrelic/java-agent/newrelic-agent/${NEW_RELIC_VERSION}/newrelic-java.zip -o newrelic-java.zip && \
    unzip newrelic-java.zip -d /newrelic && \
    rm newrelic-java.zip

# Copiar archivos de la app
COPY build.gradle gradlew ./
COPY gradle ./gradle
RUN chmod +x ./gradlew
RUN ./gradlew dependencies --no-daemon || true

COPY . .
COPY newrelic.yml /newrelic/newrelic.yml
RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app

# Copiar agente y config de New Relic desde build
COPY --from=build /newrelic /newrelic

# Copiar aplicación
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

# Lanzar con New Relic agent
ENTRYPOINT ["java", "-javaagent:/newrelic/newrelic-agent.jar", "-jar", "app.jar"]
