# syntax=docker/dockerfile:1.7

FROM eclipse-temurin:21-jdk AS build
WORKDIR /workspace

COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN --mount=type=cache,target=/root/.m2 ./mvnw -B dependency:go-offline

COPY src src
COPY books.csv books.csv
RUN --mount=type=cache,target=/root/.m2 ./mvnw -B clean package

FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app

RUN groupadd --system spring && useradd --system --gid spring spring
COPY --from=build /workspace/target/*.jar app.jar
COPY books.csv books.csv
RUN chown -R spring:spring /app

USER spring
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
