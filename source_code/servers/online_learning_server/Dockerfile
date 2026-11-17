# Build online_learning_server from source - no local JDK/Maven needed.
# Usage: docker compose -f <repo-root>/docker-compose.local.yaml up -d --build
# Note: the .env file is NOT baked into the image; compose mounts it at /app/.env
# at runtime, where Spring picks it up via spring.config.import=optional:file:.env.

# ---------- Build: compile with Maven ----------
FROM maven:3.9.16-eclipse-temurin-21 AS build
WORKDIR /build
# Warm the dependency cache in its own layer (re-runs only when pom.xml changes)
COPY pom.xml ./
RUN mvn -B -q dependency:go-offline || true
COPY src ./src
RUN mvn -B -q clean package -DskipTests

# ---------- Runtime: JRE 21 + ffmpeg (VideoUtil shells out to ffprobe) ----------
FROM eclipse-temurin:21-jre-jammy
RUN apt-get update \
    && apt-get install -y --no-install-recommends ffmpeg \
    && rm -rf /var/lib/apt/lists/*
WORKDIR /app
COPY --from=build /build/target/*.jar app.jar
# Override the Windows-only ffmpeg.path hardcoded in application.properties
# (relaxed binding: FFMPEG_PATH env var -> ffmpeg.path property)
ENV FFMPEG_PATH=/usr/bin/ffprobe
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
