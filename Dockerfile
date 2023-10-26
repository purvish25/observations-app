FROM maven:3.6-jdk-11 as build
WORKDIR /app
COPY . .
RUN mvn clean package
FROM openjdk:11-jre-slim
