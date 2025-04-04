FROM eclipse-temurin:21-jdk as build
WORKDIR /app

# Instalar herramientas necesarias
RUN apt-get update && apt-get install -y curl unzip && rm -rf /var/lib/apt/lists/*

# Descargar el agente de New Relic
ARG NEW_RELIC_VERSION=8.10.0
RUN curl -L https://download.newrelic.com/newrelic/java-agent/newrelic-agent/${NEW_RELIC_VERSION}/newrelic-java.zip -o newrelic-java.zip && \
    unzip newrelic-java.zip -d /newrelic && \
    rm newrelic-java.zip && \
    test -f /newrelic/newrelic/newrelic.jar || (echo "❌ New Relic agent not found!" && exit 1)


# Copiar archivos de la app y construir
COPY build.gradle gradlew ./
COPY gradle ./gradle
RUN chmod +x ./gradlew
RUN ./gradlew dependencies --no-daemon || true

COPY . .
COPY newrelic.yml /newrelic/newrelic.yml
RUN ./gradlew bootJar --no-daemon


FROM eclipse-temurin:21-jre
WORKDIR /app

# Copiar agente de New Relic desde la etapa de build
COPY --from=build /newrelic /newrelic

# Copiar app compilada
COPY --from=build /app/build/libs/*.jar app.jar

# Validar que el agente exista
RUN test -f /newrelic/newrelic/newrelic.jar || (echo "❌ New Relic agent not found!" && exit 1)


EXPOSE 8080

# Lanzar con New Relic
ENTRYPOINT ["java", "-javaagent:/newrelic/newrelic/newrelic.jar", "-jar", "app.jar"]