FROM openjdk:8-jdk as builder

WORKDIR /workspace
ADD gradle gradle/
ADD build.gradle.kts .
ADD settings.gradle.kts .
ADD gradlew .
ADD gists-client/build.gradle.kts gists-client/
ADD gists-client/package.json gists-client/
ADD gists-client/tsconfig.json gists-client/
ADD gists-client/yarn.lock gists-client/
ADD gists-client/src gists-client/src/
ADD gists-client/public gists-client/public/
ADD gists-server/build.gradle.kts gists-server/
ADD gists-server/src gists-server/src/

RUN ./gradlew build --no-daemon --stacktrace
RUN rm gists-server/build/libs/*-stubs.jar

FROM openjdk:8-jre

COPY --from=builder /workspace/gists-server/build/libs/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
