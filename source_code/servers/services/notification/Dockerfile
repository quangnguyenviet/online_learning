# Build notification-service from source - no local JDK/Maven needed.
# Usage: docker compose -f <repo-root>/docker-compose.local.yaml up -d --build

# ---------- Build: compile with Maven ----------
FROM maven:3.9.16-eclipse-temurin-21 AS build
WORKDIR /build
# Warm the dependency cache in its own layer (re-runs only when pom.xml changes)
COPY pom.xml ./
RUN mvn -B -q dependency:go-offline || true
COPY src ./src
RUN mvn -B -q clean package -DskipTests

# ---------- Runtime: JRE 21 ----------
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /build/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
