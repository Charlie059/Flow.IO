FROM gradle:8.6.0-jdk17 AS build
COPY --chown=gradle:gradle . /usr/src/app
WORKDIR /usr/src/app
RUN gradle :grpc-lib:generateProto :server:build --no-daemon

FROM openjdk:17

EXPOSE 8080
EXPOSE 9090

RUN mkdir /app

COPY --from=build /use/src/app/server/build/libs/*.jar /app/app.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]