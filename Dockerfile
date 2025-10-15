
FROM gradle:9.1.0-jdk21 AS builder
WORKDIR /home/gradle/poll-app
COPY . .
RUN gradle bootJar --no-daemon

FROM eclipse-temurin:21-jdk-jammy
WORKDIR /poll-app

RUN useradd -m hanna
USER hanna

COPY --from=builder /home/gradle/poll-app/build/libs/*.jar app.jar

EXPOSE 8080


CMD ["java", "-jar", "app.jar"]