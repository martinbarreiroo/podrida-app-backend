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

# Crear carpeta para New Relic y copiar archivos
RUN mkdir -p /usr/local/newrelic
COPY --from=build /app/newrelic/newrelic.jar /usr/local/newrelic/newrelic.jar
COPY --from=build /app/newrelic/newrelic.yml /usr/local/newrelic/newrelic.yml

# Copiar el .jar de la app
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

# Usar el agente
ENTRYPOINT ["java", "-javaagent:/usr/local/newrelic/newrelic.jar", "-jar", "app.jar"]
