FROM openjdk:17-alpine
WORKDIR /app
EXPOSE 8081
COPY target/ordem-servico-backend.jar ordem-servico-backend.jar
ENTRYPOINT ["java", "-jar", "/ordem-servico-backend.jar"]