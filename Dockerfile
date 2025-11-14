FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /usr/src/app
COPY . .
RUN mvn package -DskipTests


FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /usr/src/app/target/quarkus-app/ /app/

# Configura SQLite
RUN groupadd -r appgroup && useradd -r -g appgroup -u 1001 appuser

RUN mkdir /data && chown appuser:appgroup /data

VOLUME /data

EXPOSE 8080
USER appuser

CMD ["java", \
     "-Dquarkus.http.host=0.0.0.0", \
     "-Dquarkus.datasource.jdbc.url=jdbc:sqlite:/data/caixaverso.db", \
     "-jar", "quarkus-run.jar" \
    ]