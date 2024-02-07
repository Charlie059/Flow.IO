FROM gradle:8.6.0-jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle :grpc-lib:generateProto :server:build --no-daemon

FROM openjdk:17-jre-slimer

EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/src/server/build/libs/*.jar /app/app.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]