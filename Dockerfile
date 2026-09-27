# Etapa 1: compila o projeto e gera o .jar
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw -B dependency:go-offline
COPY src src
RUN ./mvnw -B package -DskipTests

# Etapa 2: imagem final, só com o Java necessário para rodar
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/tarefas.jar app.jar
USER 1000
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
