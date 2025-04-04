FROM eclipse-temurin:21-jdk as build
WORKDIR /app

# Copiar archivos de New Relic
COPY newrelic/ ./newrelic/

# Copiar archivos de gradle
COPY build.gradle gradlew ./
COPY gradle ./gradle
RUN chmod +x ./gradlew

# Cachear dependencias
RUN ./gradlew dependencies --no-daemon || true

# Copiar el código y construir
COPY . .
RUN ./gradlew bootJar --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app
ARG NEW_RELIC_LICENSE_KEY
ENV NEW_RELIC_LICENSE_KEY=$NEW_RELIC_LICENSE_KEY

# Crear carpeta para New Relic y copiar archivos
COPY --from=build /app/newrelic/newrelic.jar /app/newrelic.jar
COPY --from=build /app/newrelic/newrelic.yml /app/newrelic.yml

# Copiar el .jar de la app
COPY --from=build /app/build/libs/*.jar app.jar


EXPOSE 8080

# Usar el agente
ENTRYPOINT ["java", "-javaagent:/app/newrelic.jar", "-jar", "app.jar"]
