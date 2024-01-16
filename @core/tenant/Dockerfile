FROM maven:3.8.3-openjdk-17 as build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=build /app/target/tenant-0.0.1-SNAPSHOT.jar /app

ENTRYPOINT ["java","-jar","/app/tenant-0.0.1-SNAPSHOT.jar"]
