FROM openjdk:18

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src

ADD target/crapi.jar crapi.jar

ENTRYPOINT ["java","-jar","crapi.jar"]