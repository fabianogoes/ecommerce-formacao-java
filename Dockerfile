# ==========================================
# Stage 1: Build (Compilação)
# ==========================================
FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /app

# Copiar arquivos de configuração do Maven
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Baixar dependências (layer cacheável)
RUN ./mvnw dependency:go-offline

# Copiar código fonte
COPY src ./src

# Compilar a aplicação
RUN ./mvnw clean package -DskipTests

# ==========================================
# Stage 2: Runtime (Execução)
# ==========================================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copiar apenas o JAR do stage anterior
COPY --from=builder /app/target/ecommerce-0.0.1-SNAPSHOT.jar app.jar

# Criar usuário não-root para segurança
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]