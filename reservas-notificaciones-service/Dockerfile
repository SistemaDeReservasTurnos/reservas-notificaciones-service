# ============================
# ETAPA 1: BUILD
# ============================
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw clean package -DskipTests
RUN cp target/*.jar app.jar

# ============================
# ETAPA 2: RUNTIME
# ============================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Crear usuario
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copiar con permisos explícitos para el usuario spring
COPY --from=builder --chown=spring:spring /app/app.jar app.jar

# Configurar carpeta de secretos (esto requiere volver a root momentáneamente)
USER root
RUN mkdir -p /etc/secrets && chown spring:spring /etc/secrets
USER spring:spring

# Exponer puerto
EXPOSE 8085

# Ejecutar
ENTRYPOINT ["java", "-jar", "app.jar"]