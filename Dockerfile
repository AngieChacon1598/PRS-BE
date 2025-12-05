# ============================================
# Stage 1: Build (Maven)
# ============================================
FROM maven:3.9-eclipse-temurin-17-alpine AS build

WORKDIR /app

# Copiar archivos de configuración de Maven primero (para aprovechar cache de Docker)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente
COPY src ./src

# Compilar la aplicación (skip tests para acelerar build)
RUN mvn clean package -DskipTests -B

# ============================================
# Stage 2: Runtime (Alpine JRE)
# ============================================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Instalar dependencias necesarias para la aplicación
RUN apk add --no-cache \
    curl \
    wget \
    && rm -rf /var/cache/apk/*

# Crear usuario no-root para seguridad
RUN addgroup -S spring && adduser -S spring -G spring

# Copiar el JAR desde el stage de build
COPY --from=build /app/target/*.jar app.jar

# Cambiar ownership al usuario spring
RUN chown spring:spring app.jar

# Cambiar a usuario no-root
USER spring:spring

# Exponer el puerto de la aplicación
EXPOSE 5004

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:5004/actuator/health || exit 1

# Variables de entorno JVM optimizadas para contenedores
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:+UseG1GC"

# Ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

