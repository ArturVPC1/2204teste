# ── Etapa 1: Build com Maven ──────────────────────────
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia src por último para aproveitar cache do Maven
# mas garante que sempre recompila o código
COPY src ./src
RUN mvn clean package -DskipTests

# ── Etapa 2: Imagem final leve ────────────────────────
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

COPY --from=build /app/target/aulabd-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
