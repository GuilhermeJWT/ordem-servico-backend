FROM maven:3.8.5-openjdk-17
EXPOSE 8081
ADD target/ordem-servico-backend.jar ordem-servico-backend.jar
ENTRYPOINT ["java", "-jar", "ordem-servico-backend.jar"]