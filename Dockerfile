FROM openjdk:17-alpine
EXPOSE 8081
ADD target/ordem-servico-backend.jar ordem-servico-backend.jar
ENTRYPOINT ["java", "-jar", "/ordem-servico-backend.jar"]