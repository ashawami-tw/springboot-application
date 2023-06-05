FROM gradle:7.6-jdk19 AS build
COPY --chown=gradle:gradle ./application /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build -x test

FROM eclipse-temurin:19-jre
EXPOSE 8081
LABEL authors="ashawami"
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/application.jar
ENTRYPOINT ["java", "-jar", "/app/application.jar"]