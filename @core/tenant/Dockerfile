FROM gradle:8.6.0-jdk17 AS build
COPY --chown=gradle:gradle . /usr/src/app
WORKDIR /usr/src/app
RUN gradle :grpc-lib:generateProto :server:build --no-daemon

FROM openjdk:17

EXPOSE 8080
EXPOSE 9090

RUN mkdir /app

COPY --from=build /usr/src/app/server/build/libs/server.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
