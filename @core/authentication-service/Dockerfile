FROM maven:3.8.3-openjdk-17 as build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/authentication-service-0.0.1-SNAPSHOT.jar /app
ENTRYPOINT ["java","-jar","/app/authentication-service-0.0.1-SNAPSHOT.jar"]
