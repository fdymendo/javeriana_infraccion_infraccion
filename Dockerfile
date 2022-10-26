FROM amazoncorretto:17-alpine-jdk as builder
WORKDIR application
ARG JAR_FILE=gradle/wrapper/*.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java","-jar", "application.jar"]